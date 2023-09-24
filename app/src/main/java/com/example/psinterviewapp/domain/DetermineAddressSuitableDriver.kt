package com.example.psinterviewapp.domain

import android.util.Log
import javax.inject.Inject

class DetermineAddressSuitableDriver @Inject constructor() {

    private val vowelList = listOf('a', 'e', 'i', 'o', 'u')

    /**
     * The algorithm for determine the most suitable driver is as follows:
     *
     * ● If the length of the shipment's destination street name is even, the base suitability score
     * (SS) is the number of vowels in the driver’s name multiplied by 1.5.
     * ● If the length of the shipment's destination street name is odd, the base SS is the number
     * of consonants in the driver’s name multiplied by 1.
     * ● If the length of the shipment's destination street name shares any common factors
     * (besides 1) with the length of the driver’s name, the SS is increased by 50% above the
     * base SS.
     *
     * We will iterate over every driver for an individual address, finding the most suitable for
     * a driver.
     *
     * @param driverNames the full list of potential drivers for an address.
     * @param address the address to assign to a potential driver.
     * @return Pair type where left is driverName and right is address.
     */
    operator fun invoke(driverNames: Collection<String>, address: String): Pair<String, String>? {
        val streetName = retrieveStreetName(address)
        val streetNameCharacterCountEven = streetName.length % 2 == 0
        var currentSsCount = 0
        var bestFitDriver = ""

        driverNames.forEach { driverName ->
            val driverNameCharCountIsEven = driverName.length % 2 == 0

            // Our common factor here is equal length, or both are odd or even.
            val driverNameAndStreetNameHaveCommonFactor =
                driverName.length == streetName.length
                    || (streetNameCharacterCountEven && driverNameCharCountIsEven)
                    || (!streetNameCharacterCountEven && !driverNameCharCountIsEven)

            // These base scores are based on the vowel or consonant count of the driver.
            // Making the driverName lowercase so we don't have to check capital vowels here
            var baseSsScore: Int = if (streetNameCharacterCountEven) {
                driverName.lowercase()
                    .filter { driverChar -> vowelList.contains(driverChar) }
                    .length * 1.5
            } else {
                // Replacing spaces here, as they will count as "consonants" based on the
                // !vowelList.contains call.
                driverName
                    .replace("\\s".toRegex(), "")
                    .lowercase()
                    .filter { driverChar -> !vowelList.contains(driverChar) }
                    .length
            }.toInt()

            if (driverNameAndStreetNameHaveCommonFactor) {
                baseSsScore += (baseSsScore * 0.5).toInt()
            }

            if (baseSsScore > currentSsCount) {
                bestFitDriver = driverName
                currentSsCount = baseSsScore
            }
        }

        return if (bestFitDriver.isNotBlank() && address.isNotBlank()) {
            bestFitDriver to address
        } else {
            null
        }
    }

    /*
     * Removes all numbers and spaces from the full address, then trims the
     * address to just the first two words, which for our use case, is always
     * the street address.
     */
    private fun retrieveStreetName(fullAddress: String): String {
        return fullAddress.replace("[0-9]".toRegex(), "")
            .trim()
            .split(" ")
            .take(2)
            .joinToString(separator = "")
    }
}
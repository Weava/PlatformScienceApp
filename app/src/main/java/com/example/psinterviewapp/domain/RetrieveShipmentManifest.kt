package com.example.psinterviewapp.domain

import android.util.Log
import com.example.psinterviewapp.data.ShipmentManifest
import com.example.psinterviewapp.data.ShipmentManifestResult
import com.example.psinterviewapp.data.ShipmentRepository
import javax.inject.Inject

class RetrieveShipmentManifest @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
    private val determineHighestScoreForDriver: DetermineAddressSuitableDriver
) {
    suspend operator fun invoke(): SuitableShippingResult {
        return when (
            val manifestResult = shipmentRepository.retrieveShipmentManifest()
        ) {
            ShipmentManifestResult.IsEmpty ->
                SuitableShippingResult.NoValidMap
            is ShipmentManifestResult.ManifestPopulated ->
                convertManifestToSuitabilityMap(manifestResult.shipmentManifest)
                    .let { SuitableShippingResult.Success(it) }
        }

    }

    private fun convertManifestToSuitabilityMap(shipmentManifest: ShipmentManifest): Map<String, String> {
        val suitableManifestMap = mutableMapOf<String, String>()
        val driversList = shipmentManifest.driverNames.toMutableList()
        for (address in shipmentManifest.addresses) {
            val driverAddressPair = determineHighestScoreForDriver(driversList, address)
            if (driverAddressPair != null) {
                suitableManifestMap[driverAddressPair.first] = driverAddressPair.second
                driversList.remove(driverAddressPair.first)
            }
        }

        return suitableManifestMap
    }
}
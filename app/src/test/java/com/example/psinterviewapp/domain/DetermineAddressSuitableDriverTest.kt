package com.example.psinterviewapp.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DetermineAddressSuitableDriverTest {

    private val determineAddressSuitableDriver = DetermineAddressSuitableDriver()

    @Test
    fun `invoke - given shipment even and driver has lots vowels, should pair vowel driver to shipment`() {
        // Given
        val address = "123 Drewberry Ave Apt. 23"
        val driverOne = "Aeiou Holiday"
        val driverTwo = "Bdfgk McCall"
        val driverList = listOf(driverOne, driverTwo)

        // When
        val actual = determineAddressSuitableDriver(driverList, address)

        // Then
        assertThat(actual).isEqualTo(driverOne to address)
    }

    @Test
    fun `invoke - given shipment odd and driver has lots vowels, should pair vowel driver to shipment`() {
        // Given
        val address = "123 Drewberry Lane Apt. 23"
        val driverOne = "Aeiou Holiday"
        val driverTwo = "Bdfgk McCall"
        val driverList = listOf(driverOne, driverTwo)

        // When
        val actual = determineAddressSuitableDriver(driverList, address)

        // Then
        assertThat(actual).isEqualTo(driverTwo to address)
    }

    @Test
    fun `invoke - given empty driver list, driver pair is null`() {
        // Given
        val address = "123 Drewberry Lane Apt. 23"
        val driverList = emptyList<String>()

        // When
        val actual = determineAddressSuitableDriver(driverList, address)

        // Then
        assertThat(actual).isNull()
    }

    @Test
    fun `invoke - given empty address, driver pair is null`() {
        // Given
        val address = ""
        val driverList = emptyList<String>()

        // When
        val actual = determineAddressSuitableDriver(driverList, address)

        // Then
        assertThat(actual).isNull()
    }
}
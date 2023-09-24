package com.example.psinterviewapp.domain

import com.example.psinterviewapp.data.ShipmentManifest
import com.example.psinterviewapp.data.ShipmentManifestResult
import com.example.psinterviewapp.data.ShipmentRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.given

class RetrieveShipmentManifestTest {

    private val shipmentRepository: ShipmentRepository = mock()
    private val determineHighestScoreForDriver: DetermineAddressSuitableDriver = mock()
    private val retrieveShipmentManifest: RetrieveShipmentManifest =
        RetrieveShipmentManifest(
            shipmentRepository = shipmentRepository,
            determineHighestScoreForDriver = determineHighestScoreForDriver
        )

    @Test
    fun `invoke - given manifest populated, should convert to map`() = runTest {
        // Given
        val driverOne = "Kurt Russell"
        val driverTwo = "Val Kilmer"
        val driverList = listOf(driverOne, driverTwo)
        val addressOne = "123 Cool Lane"
        val addressTwo = "456 Sick Ave"
        val shipmentManifest = ShipmentManifest(
            addresses = listOf(addressOne, addressTwo),
            driverNames = driverList
        )
        val expectedMap = mapOf(driverOne to addressOne, driverTwo to addressTwo)
        given(shipmentRepository.retrieveShipmentManifest())
            .willReturn(ShipmentManifestResult.ManifestPopulated(shipmentManifest))
        `when`(determineHighestScoreForDriver.invoke(driverList, addressOne))
            .thenReturn(driverOne to addressOne)
        `when`(determineHighestScoreForDriver.invoke(listOf(driverTwo), addressTwo))
            .thenReturn(driverTwo to addressTwo)

        // When
        val actual = retrieveShipmentManifest()

        // Then
        actual as SuitableShippingResult.Success
        assertThat(actual.driverToAddressMap).isEqualTo(expectedMap)
    }

    @Test
    fun `invoke - given manifest empty, should return empty`() = runTest {
        // Given
        given(shipmentRepository.retrieveShipmentManifest())
            .willReturn(ShipmentManifestResult.IsEmpty)

        // When
        val actual = retrieveShipmentManifest()

        // Then
        assertThat(actual).isInstanceOf(SuitableShippingResult.NoValidMap::class.java)
    }
}
package com.example.psinterviewapp.data

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given
import org.mockito.kotlin.never
import org.mockito.kotlin.then

class ShipmentRepositoryTest {

    private val shipmentFileStore: ShipmentFileStore = mock()
    private val shipmentInMemoryStore: ShipmentInMemoryStore = mock()
    private val shipmentRepository = ShipmentRepository(
        shipmentFileStore = shipmentFileStore,
        shipmentInMemoryStore = shipmentInMemoryStore
    )

    @Test
    fun `retrieveShipmentManifest - given no manifest in memory, should get from file`() = runTest {
        // Given
        val shipManifest = ShipmentManifest(emptyList(), emptyList())
        given(shipmentInMemoryStore.retrieve())
            .willReturn(null)
        given(shipmentFileStore.getShipmentManifest())
            .willReturn(ShipmentManifestRetrievalResult.Success(shipManifest))

        // When
        val result = shipmentRepository.retrieveShipmentManifest()

        // Then
        then(shipmentInMemoryStore).should().store(shipManifest)
        result as ShipmentManifestResult.ManifestPopulated
        assertThat(result.shipmentManifest).isEqualTo(shipManifest)
    }

    @Test
    fun `retrieveShipmentManifest - given manifest in memory, should get from memory`() = runTest {
        // Given
        val shipManifest = ShipmentManifest(emptyList(), emptyList())
        given(shipmentInMemoryStore.retrieve())
            .willReturn(shipManifest)

        // When
        val result = shipmentRepository.retrieveShipmentManifest()

        // Then
        then(shipmentInMemoryStore).should(never()).store(shipManifest)
        result as ShipmentManifestResult.ManifestPopulated
        assertThat(result.shipmentManifest).isEqualTo(shipManifest)
    }

    @Test
    fun `retrieveShipmentManifest - given manifest not found, should return error`() = runTest {
        // Given
        given(shipmentInMemoryStore.retrieve())
            .willReturn(null)
        given(shipmentFileStore.getShipmentManifest())
            .willReturn(ShipmentManifestRetrievalResult.Error.EmptyOrNullFile)

        // When
        val result = shipmentRepository.retrieveShipmentManifest()

        // Then
        assertThat(result).isInstanceOf(ShipmentManifestResult.IsEmpty::class.java)
    }

    @Test
    fun `retrieveShipmentManifest - given manifest exception, should return error`() = runTest {
        // Given
        given(shipmentInMemoryStore.retrieve())
            .willReturn(null)
        given(shipmentFileStore.getShipmentManifest())
            .willReturn(ShipmentManifestRetrievalResult.Error.Exception(mock()))

        // When
        val result = shipmentRepository.retrieveShipmentManifest()

        // Then
        assertThat(result).isInstanceOf(ShipmentManifestResult.IsEmpty::class.java)
    }
}
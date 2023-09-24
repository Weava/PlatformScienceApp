package com.example.psinterviewapp.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShipmentRepository @Inject constructor(
    private val shipmentFileStore: ShipmentFileStore,
    private val shipmentInMemoryStore: ShipmentInMemoryStore
) {
    suspend fun retrieveShipmentManifest(): ShipmentManifestResult {
        val manifest = shipmentInMemoryStore.retrieve()
        return if (manifest == null) {
            val result = withContext(Dispatchers.IO) {
                shipmentFileStore.getShipmentManifest()
            }
            when (result) {
                ShipmentManifestRetrievalResult.Error.EmptyOrNullFile -> ShipmentManifestResult.IsEmpty
                is ShipmentManifestRetrievalResult.Error.Exception -> ShipmentManifestResult.IsEmpty
                is ShipmentManifestRetrievalResult.Success -> {
                    shipmentInMemoryStore.store(result.shipmentManifest)
                    ShipmentManifestResult.ManifestPopulated(result.shipmentManifest)
                }
            }
        } else {
            ShipmentManifestResult.ManifestPopulated(manifest)
        }
    }
}
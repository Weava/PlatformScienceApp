package com.example.psinterviewapp.data

sealed class ShipmentManifestResult {

    object IsEmpty : ShipmentManifestResult()

    data class ManifestPopulated(
        val shipmentManifest: ShipmentManifest
    ) : ShipmentManifestResult()
}
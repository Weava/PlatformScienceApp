package com.example.psinterviewapp.data

sealed class ShipmentManifestRetrievalResult {

    data class Success(val shipmentManifest: ShipmentManifest): ShipmentManifestRetrievalResult()

    sealed class Error : ShipmentManifestRetrievalResult() {

        object EmptyOrNullFile : Error()

        data class Exception(val throwable: Throwable) : Error()
    }
}
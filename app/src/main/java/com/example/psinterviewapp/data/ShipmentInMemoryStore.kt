package com.example.psinterviewapp.data

import com.example.psinterviewapp.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class ShipmentInMemoryStore @Inject constructor() {
    private var shipmentManifest: ShipmentManifest? = null

    fun store(shipmentManifest: ShipmentManifest) {
        this.shipmentManifest = shipmentManifest
    }

    fun retrieve() = shipmentManifest
}
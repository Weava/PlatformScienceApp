package com.example.psinterviewapp.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShipmentManifest(
    @Json(name = "shipments")
    val addresses: List<String>,

    @Json(name = "drivers")
    val driverNames: List<String>
)
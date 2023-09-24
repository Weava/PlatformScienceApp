package com.example.psinterviewapp.domain

sealed class SuitableShippingResult {

    data class Success(val driverToAddressMap: Map<String, String>): SuitableShippingResult()

    object NoValidMap : SuitableShippingResult()
}
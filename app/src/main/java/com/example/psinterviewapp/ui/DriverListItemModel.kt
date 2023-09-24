package com.example.psinterviewapp.ui

data class DriverListItemModel(
    val driverName: String,
    val deliveryAddress: String,
    val isExpanded: Boolean = false
)
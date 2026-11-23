package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val id: String,
    var latitude: Double,
    var longitude: Double
)
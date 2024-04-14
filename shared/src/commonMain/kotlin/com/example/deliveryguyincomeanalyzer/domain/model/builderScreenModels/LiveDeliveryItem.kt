package com.example.deliveryguyincomeanalyzer.domain.model.builderScreenModels

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class LiveDeliveryItem(
    val time : LocalDateTime,
    val extra : Float
)
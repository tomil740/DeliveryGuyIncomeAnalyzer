package com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models

data class DataPerHourDomain(
    val hour : Int,//(will be const between 1-24)
    val extraIncome : Float,
    val baseIncome : Float, //(define specific hour if the platform will update that will stay the same …)
    val delivers : Int
)

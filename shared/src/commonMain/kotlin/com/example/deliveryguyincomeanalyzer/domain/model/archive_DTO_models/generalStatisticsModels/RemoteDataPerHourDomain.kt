package com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels

import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.DataPerHourDomain

data class RemoteDataPerHourDomain(
    val dataPerHourDomain: DataPerHourDomain,
    val totalObjects : Int
)

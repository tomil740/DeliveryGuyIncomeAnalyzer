package com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.generalStatisticsModels

data class RemoteWorkDeclareDomain(
    val workingPlatformId : String,
    val startTime:Float,
    val endTime : Float,
    val totalTime : Float = endTime-startTime,
    val theCounter:Int
)

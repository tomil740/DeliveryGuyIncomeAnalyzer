package com.example.deliveryguyincomeanalyzer.domain.model.util

data class PlatformIdComponents(
    val platformName:String,
    val workingZone : String
)
fun getPlatformIdComponents(platformId:String):PlatformIdComponents{
    val a= platformId.indexOf('-')
    val name = platformId.substring(0,a)
    val zone =platformId.substring(a)

    return  PlatformIdComponents(
        platformName = name, workingZone = zone
    )

}
package com.example.deliveryguyincomeanalyzer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
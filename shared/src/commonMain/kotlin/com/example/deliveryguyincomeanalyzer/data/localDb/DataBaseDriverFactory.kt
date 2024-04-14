package com.example.deliveryguyincomeanalyzer.data.localDb

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}
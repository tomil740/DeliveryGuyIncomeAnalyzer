package com.example.deliveryguyincomeanalyzer.data.localDb

import android.content.Context
import com.example.deliveryguyincomeanalyzer.database.WorkData
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            WorkData.Schema,
            context = context,
            "workData.db"
        )
    }

}


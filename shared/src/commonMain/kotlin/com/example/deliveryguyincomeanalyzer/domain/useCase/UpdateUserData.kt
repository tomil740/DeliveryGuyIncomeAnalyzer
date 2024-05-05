package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

class UpdateUserData(val repository: Repository) {
    operator fun invoke(){
        val theTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)

        repository.updateUserData("${theTime.year}${theTime.month.number}")
    }

}
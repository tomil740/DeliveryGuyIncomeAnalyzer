package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository

class GetMonthWorkDeclareAmount(val repository: Repository) {
    operator fun invoke(monthId:String,workingPlatform:String):Int{
       return repository.getMonthSumWorkSessionAmount(monthId = monthId,workingPlatform=workingPlatform)

    }

}
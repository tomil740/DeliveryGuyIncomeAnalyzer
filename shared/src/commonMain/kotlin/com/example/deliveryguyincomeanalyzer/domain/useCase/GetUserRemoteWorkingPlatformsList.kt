package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository

class GetUserRemoteWorkingPlatformsList(val repository: Repository) {
     operator fun invoke():List<String>{
        val remoteMenu = repository.getAllRemoteWorkingPlatformsId().filter { it.last() != 'X' }
        val resultList = mutableListOf<String>()

        for (i in remoteMenu){
           if(repository.getWorkingPlatformById(i)!=null)
               resultList.add(i)
        }

        return resultList
    }
}
package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.WorkingPlatform
import com.example.deliveryguyincomeanalyzer.domain.model.util.getPlatformIdComponents

/*
InsertWorkingPlatform:
will get his data from the platform builder and insert matched local working platform accordingly
In order of implementing the use of working platform menu we need to get
* two step pick of an object(name -> working zone),[1]
* get only the custom object (to not get duplicate name when merged with the remote objects ),[2]
* get the "override" object by the matched Id to the remote object
in practice :
[1] implementation :
to get all of the name we will save each platform name under the X work zone , then pull it by X...(to a new custom working platform)
then for the workZones of each platform , its easy as pull by platform name...
[2] implementation :
to get all of the custom platform we will insert the object attribute "isCustom" true, and pull accordingly

 */
class InsertWorkingPlatform(val repository: Repository) {
    operator fun invoke(workingPlatform: WorkingPlatform){
        //we will start by checking if the platform name is new..., if its we will add the basic menu item ...
        val platformIdComponents = getPlatformIdComponents(workingPlatform.name)
        if (repository.getWorkingPlatformById("${platformIdComponents.platformName}x")==null){
            repository.insertWorkingPlatform(workingPlatform.copy(name = "${platformIdComponents.platformName}X"))
        }
        repository.insertWorkingPlatform(workingPlatform)



    }

}
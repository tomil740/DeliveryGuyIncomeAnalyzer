package com.example.deliveryguyincomeanalyzer.domain.useCase

import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.model.archive_DTO_models.WorkSumDomain
import com.example.deliveryguyincomeanalyzer.domain.model.theModels.SumObjectInterface
import com.example.deliveryguyincomeanalyzer.domain.model.util.closeTypesCollections.SumObjectSourceType


/*
GetTopLevelGeneralStatisticsSumObj
arguments : isMonth:Boolean , wp:String ,
 */
class GetTopLevelGeneralStatisticsSumObj(val repository: Repository) {
    operator fun invoke(isMonthSum:Boolean=true,workPlatform:String,basicWorkSum:WorkSumDomain):WorkSumDomain{
       var amount = repository.getRemoteSumObjectStatisticsByWp(workPlatform).monthAmount
        val dataList = mutableListOf<WorkSumDomain>()
        //Todo an fix just as for now when its offline and I click on some miss example data
        if(amount == 0) {
            amount = 1
        }
        repeat(amount){
            dataList.add(basicWorkSum)
        }
        return SumDomainData().getSummarizesDomainObject(a= dataList)
    }
}
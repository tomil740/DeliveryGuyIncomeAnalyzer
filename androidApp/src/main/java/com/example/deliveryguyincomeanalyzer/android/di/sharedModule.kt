package com.example.deliveryguyincomeanalyzer.android.di

import com.example.deliveryguyincomeanalyzer.data.RepositoryImpl
import com.example.deliveryguyincomeanalyzer.data.localDb.DatabaseDriverFactory
import com.example.deliveryguyincomeanalyzer.database.WorkData
import com.example.deliveryguyincomeanalyzer.domain.Repository
import com.example.deliveryguyincomeanalyzer.domain.useCase.DeclareBuilderUseCases
import com.example.deliveryguyincomeanalyzer.domain.useCase.DeleteLiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetDeclareDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetDeclareShifts
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetLastWorkSessionSum
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetLiveBuilderState
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetAllTimeMonthData
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetGeneralStatisticsSumObj
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetMonthSum
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetMonthWorkDeclareAmount
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetRemoteDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetRemoteWorkDeclare
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetShiftTypeStatisticsData
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetTopLevelGeneralStatisticsSumObj
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetUserRemoteWorkingPlatformsList
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetWorkSessionStatisticsData
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetWorkingPlatformById
import com.example.deliveryguyincomeanalyzer.domain.useCase.GetWorkingPlatformMenu
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertLiveDeliveryState
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertShiftObj
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertWorkDeclare
import com.example.deliveryguyincomeanalyzer.domain.useCase.ObjectItemUseCases
import com.example.deliveryguyincomeanalyzer.domain.useCase.ShouldUpdateRemoteStatistics
import com.example.deliveryguyincomeanalyzer.domain.useCase.SumDomainData
import com.example.deliveryguyincomeanalyzer.domain.useCase.UpdateRemoteDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.UpdateRemoteSumObj
import com.example.deliveryguyincomeanalyzer.domain.useCase.UpdateRemoteWorkDeclare
import com.example.deliveryguyincomeanalyzer.domain.useCase.UpdateUserData
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderViewmodel
import com.example.deliveryguyincomeanalyzer.presentation.objectItemScreen.ObjectItemViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sharedModule = module {
    single<Repository> {
        RepositoryImpl(
            WorkData(DatabaseDriverFactory(this.androidContext()).create())
        )
    }

    single<DeclareBuilderUseCases> {
        DeclareBuilderUseCases(
            insertLiveDeliveryState = InsertLiveDeliveryState(get()),
            getLiveDeliveryState = GetLiveBuilderState(get()),
            deleteLiveBuilderState = DeleteLiveBuilderState(get()),
            getDeclareDataPerHour = GetDeclareDataPerHour(),
            getDeclareShifts = GetDeclareShifts(),
            insertDataPerHour = InsertDataPerHour(get()),
            insertShiftObj = InsertShiftObj(get()),
            insertWorkDeclare = InsertWorkDeclare(get()),
            getLastWorkSessionSum = GetLastWorkSessionSum(get()),
            getWorkingPlatformById = GetWorkingPlatformById(get()),
            getWorkingPlatformMenu = GetWorkingPlatformMenu(get()),
            getAllTimeMonthData = GetAllTimeMonthData(get()),
            getWorkSessionStatisticsData = GetWorkSessionStatisticsData(get()),
            getRemoteWorkDeclare = GetRemoteWorkDeclare(get()),
            updateRemoteWorkDeclare = UpdateRemoteWorkDeclare(get()),
            getRemoteDataPerHour = GetRemoteDataPerHour(get()),
            updateRemoteDataPerHour = UpdateRemoteDataPerHour(get())

        )
    }

    single<ObjectItemUseCases> {
        ObjectItemUseCases(
            getLastWorkSessionSum = GetLastWorkSessionSum(get()),
            getMonthSum = GetMonthSum(get()),
            getAllTimeMonthData = GetAllTimeMonthData(get()),
            sumDomainData = SumDomainData(),
            getWorkSessionStatisticsData = GetWorkSessionStatisticsData(get()),
            getShiftTypeStatisticsData = GetShiftTypeStatisticsData(get()),
            getWorkingPlatformMenu = GetWorkingPlatformMenu(get()),
            getRemoteDataPerHour = GetRemoteDataPerHour(get()),
            getRemoteWorkDeclare = GetRemoteWorkDeclare(get()),
            getDeclareShifts = GetDeclareShifts(),
            getGeneralStatisticsSumObj = GetGeneralStatisticsSumObj(get()),
            getWorkingPlatformById = GetWorkingPlatformById(get()),
            getMonthWorkDeclareAmount = GetMonthWorkDeclareAmount(get()),
            getUserRemoteWorkingPlatformsList = GetUserRemoteWorkingPlatformsList(get()),
            shouldUpdateRemoteStatistics = ShouldUpdateRemoteStatistics(get()),
            updateRemoteSumObj = UpdateRemoteSumObj(get()),
            updateUserData = UpdateUserData(get()),
            getTopLevelGeneralStatisticsSumObj = GetTopLevelGeneralStatisticsSumObj(get())
        )
    }


    viewModel{
        ObjectItemViewmodel(get())
    }
    

    viewModel {
        DeclareBuilderViewmodel(get())
    }


}


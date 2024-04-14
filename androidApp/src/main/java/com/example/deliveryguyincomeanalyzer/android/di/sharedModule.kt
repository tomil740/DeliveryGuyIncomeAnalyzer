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
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertDataPerHour
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertLiveDeliveryState
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertShiftObj
import com.example.deliveryguyincomeanalyzer.domain.useCase.InsertWorkDeclare
import com.example.deliveryguyincomeanalyzer.presentation.declareBuilderScreen.DeclareBuilderViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.get
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
            getLastWorkSessionSum = GetLastWorkSessionSum(get())
        )
    }

    viewModel {
        DeclareBuilderViewmodel(get())
    }


}


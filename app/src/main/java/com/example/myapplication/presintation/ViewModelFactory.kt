package com.example.myapplication.presintation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import data.dataSource.MetroJsonDataSource
import data.repo.MetroRepositoryImpl
import domain.usecase.*

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val dataSource = MetroJsonDataSource(context)
            val repository = MetroRepositoryImpl(dataSource)

            val fare = CalculateFareUseCase(repository)
            val time = CalculateTimeUseCase(repository)
            val bfsUseCase = BFSUseCase()

            val findRoute = FindRouteUseCase(repository, fare, time, bfsUseCase)

            @Suppress("UNCHECKED_CAST")
            return MainViewModel(findRoute, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

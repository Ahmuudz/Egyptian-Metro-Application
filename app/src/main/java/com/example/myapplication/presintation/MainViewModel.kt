package com.example.myapplication.presintation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.models.RouteResult
import domain.repo.MetroRepository
import domain.usecase.FindRouteUseCase

class MainViewModel(
    private val findRouteUseCase: FindRouteUseCase,
    private val repository: MetroRepository
) : ViewModel() {

    private val _routeResult = MutableLiveData<RouteResult>()
    val routeResult: LiveData<RouteResult> = _routeResult

    private val _stations = MutableLiveData<List<String>>()
    val stations: LiveData<List<String>> = _stations

    init {
        loadStations()
    }

    private fun loadStations() {
        val allStations = repository.getStations().map { it.name }.distinct().sorted()
        _stations.value = allStations
    }

    fun findRoute(start: String, end: String) {
        if (start.isBlank() || end.isBlank()) {
            _routeResult.value = RouteResult.Error("Please enter both start and end stations")
            return
        }
        val result = findRouteUseCase(start, end)
        _routeResult.value = result
    }
}

package domain.repo

import domain.models.Stations

interface MetroRepository {
    fun getStations(): List<Stations>
    fun getTravelTime(): Int
}
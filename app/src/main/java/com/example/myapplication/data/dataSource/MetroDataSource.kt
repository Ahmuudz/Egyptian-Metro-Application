package data.dataSource

import data.models.StationsDto

interface MetroDataSource {
    fun loadStations(): List<StationsDto>
    fun getTravelTime(): Int
}
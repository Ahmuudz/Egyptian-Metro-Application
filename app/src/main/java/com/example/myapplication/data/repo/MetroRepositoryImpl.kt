package data.repo

import data.dataSource.MetroDataSource
import data.mapper.MetroMapper
import domain.models.Stations
import domain.repo.MetroRepository

class MetroRepositoryImpl (private val dataSource: MetroDataSource) : MetroRepository {
    override fun getStations(): List<Stations> {

    return    dataSource.loadStations().map {
            MetroMapper.toDomain(it)
        }

    }

    override fun getTravelTime(): Int {
      return  dataSource.getTravelTime()
    }
}
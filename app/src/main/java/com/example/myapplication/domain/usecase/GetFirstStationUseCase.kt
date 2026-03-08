package domain.usecase

import domain.models.MetroLine
import domain.models.Stations
import domain.repo.MetroRepository

class GetFirstStationUseCase(val repo : MetroRepository) {
    fun execute (line: MetroLine): String{
        return repo.getStations()
            .filter { it.line == line }
            .minBy { it.order }
            .name

    }

    companion object
}
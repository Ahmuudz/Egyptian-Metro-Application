package domain.usecase

import domain.models.MetroLine
import domain.models.Stations
import domain.repo.MetroRepository

class GetLastStationUseCase(val repo: MetroRepository) {
   operator fun invoke (line: MetroLine): String{
        return repo.getStations()
            .filter { it.line == line }
            .maxBy { it.order }
            .name
    }
}
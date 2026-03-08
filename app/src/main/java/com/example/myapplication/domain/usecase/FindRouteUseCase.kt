package domain.usecase

import domain.models.RouteResult
import domain.models.Stations
import domain.repo.MetroRepository

class FindRouteUseCase(
    private val repo: MetroRepository,
    private val calculateFareUseCase: CalculateFareUseCase,
    private val calculateTimeUseCase: CalculateTimeUseCase,
    val bfsUseCase: BFSUseCase
) {

    operator fun invoke(startName: String, endName: String): RouteResult {
        val stations = repo.getStations()
        val start = stations.find { it.name.equals(startName.trim(), true) }
            ?: RouteResult.Error("Start Station Not Found")
        val end = stations.find { it.name.equals(endName.trim(), true) }
            ?: RouteResult.Error("End Station Not Found")

        val path = bfsUseCase(start as Stations, end as Stations,stations)?:return RouteResult.Error("Path Not Found")

        val fare = calculateFareUseCase(path.size)
        val time = calculateTimeUseCase(path.size)
        return RouteResult.Success(
            path ,
            fare,
            time
        )

    }

}
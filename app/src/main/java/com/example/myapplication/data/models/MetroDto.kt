package data.models

import data.models.StationsDto

data class MetroDto(
    val stations : List<StationsDto>,
    val travel_time_between_stations_minutes : Int
)
package domain.usecase

import domain.models.MetroLine
import domain.models.Stations

class GetDirectionUseCase(
    private val getFirstStationUseCase: GetFirstStationUseCase,
    private val getLastStationUseCase: GetLastStationUseCase

) {
    fun execute(
        current: Stations,
        next: Stations,
    ): String {
        val first = getFirstStationUseCase.execute(current.line)
        val last = getLastStationUseCase.invoke(current.line)

        return if (next.order > current.order) {
            last
        } else {
            first
        }
    }
}



package di

import android.content.Context
import data.dataSource.MetroJsonDataSource
import data.repo.MetroRepositoryImpl
import domain.repo.MetroRepository
import domain.usecase.*
import presentation.ConsoleController

object AppModule {

    fun provideController(context: Context): ConsoleController {

        val dataSource = MetroJsonDataSource(context)

        val repository =
            MetroRepositoryImpl(
                dataSource
            )

        val fare = CalculateFareUseCase(repository)
        val time = CalculateTimeUseCase(repository)
        val bfsUseCase = BFSUseCase()

        val findRoute =
            FindRouteUseCase(repository, fare, time, bfsUseCase)
        val getFirstStationUseCase = GetFirstStationUseCase(repository)
        val getLastStationUseCase = GetLastStationUseCase(repository)
        val direction = GetDirectionUseCase(getFirstStationUseCase, getLastStationUseCase)

        return ConsoleController(
            findRoute,
            direction
        )
    }
}

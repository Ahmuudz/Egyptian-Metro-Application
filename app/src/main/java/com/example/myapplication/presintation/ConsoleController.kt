package presentation

import domain.models.RouteResult
import domain.models.Stations
import domain.usecase.FindRouteUseCase
import domain.usecase.GetDirectionUseCase


class ConsoleController(
    private val findRoute: FindRouteUseCase,
    private val getDirectionUseCase: GetDirectionUseCase
) {

    fun start() {

        println("===================================")
        println("=     Cairo Metro Route Finder    =")
        println("===================================")

        print("\nEnter start station: ")
        val start = readln()

        print("Enter end station: ")
        val end = readln()

        when (val result = findRoute(start, end)) {

            is RouteResult.Error ->
                println("\n{{{ ERROR }}} : ${result.message}")

            is RouteResult.Success ->
                showSuccess(result)
        }
    }

    private fun showSuccess(result: RouteResult.Success) {

        println("\nRoute:")
        println("-----------------------------------")

        val stations = result.stations

        var i = 0

        while (i < stations.size) {

            val current = stations[i]

            println("• ${current.name} (${current.line})")

            if (i < stations.size - 1) {

                val next = stations[i + 1]

                if (current.name == next.name && current.line != next.line) {

                    val direction =
                        if (i + 2 < stations.size) {

                            val afterTransfer = stations[i + 2]

                            getDirectionUseCase.execute(
                                next,
                                afterTransfer,
                            )

                        } else ""

                    println("{{{{ -> Take direction towards $direction  <- }}}}")

                    // skip duplicate transfer station
                    i++
                }
            }

            i++
        }

        println("\n-----------------------------------")
        println("Stations: ${result.stations.distinctBy { it.name }.size}")
        println("Time: ${result.time} min")
        println("Fare: ${result.fare} EGP")
    }

}


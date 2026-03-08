package domain.usecase

import domain.models.MetroLine
import domain.models.Stations
import java.util.LinkedList
import java.util.Queue

class BFSUseCase {

    // search algo
    operator fun invoke(
        start: Stations,
        end: Stations,
        stations: List<Stations>
    ): List<Stations>? {

        if (start == end) return listOf(start)

        // queue -> Stations
        // set -> Visited
        // map -> map(current,parent)

        val queue: Queue<Stations> = LinkedList()
        val visited = mutableSetOf<Stations>()
        val parent = mutableMapOf<Stations, Stations?>()

        queue.add(start)
        visited.add(start)
        parent[start] = null

        val stationsByLine = stations.groupBy { it.line }
        val stationsByName = stations.groupBy { it.name }

        while (queue.isNotEmpty()) {

            val current = queue.poll()

            if (current == end) {
                // build path
                return buildPath(end,parent)
            }

            // find neighbors
            val neighbors = getNeighbors(
                current,
                stationsByLine,
                stationsByName
            )

            for (neighbor in neighbors) {

                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                    parent[neighbor] = current
                }
            }
        }
    return null
    }

    private fun getNeighbors(
        stations: Stations,
        stationsByLine: Map<MetroLine, List<Stations>>,
        stationsByName: Map<String, List<Stations>>
    ): List<Stations> {

        val sameLine = stationsByLine[stations.line]?.filter {
            it.order == stations.order + 1 ||
                    it.order == stations.order - 1
        } ?: emptyList()

        val transfers =
            if (stations.isTransfer) {
                stationsByName[stations.name]?.filter {
                    it.line != stations.line
                } ?: emptyList()
            } else {
                emptyList<Stations>()
            }

        return sameLine + transfers
    }

    private fun buildPath(
        end: Stations,
        parent: Map<Stations, Stations?>
    ) : List<Stations> {

        val path = mutableListOf<Stations>()
        var current: Stations? = end

        while (current != null) {

            path.add(current)
            current = parent[current]

        }
        return path.reversed()
    }

}

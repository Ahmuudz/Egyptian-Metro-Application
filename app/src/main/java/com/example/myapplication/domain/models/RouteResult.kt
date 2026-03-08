package domain.models


sealed class RouteResult {
    data class Success (
        val stations : List<Stations>,
        val fare: Int,
        val time: Int
    ): RouteResult()

    data class Error (val message: String) : RouteResult()
}
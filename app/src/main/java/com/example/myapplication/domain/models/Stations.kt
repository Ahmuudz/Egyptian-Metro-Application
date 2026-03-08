package domain.models

data class Stations(
    val id: Int,
    val name: String,
    val line: MetroLine,
    val order: Int,
    val isTransfer: Boolean,
    val transferLines: List<MetroLine>
)
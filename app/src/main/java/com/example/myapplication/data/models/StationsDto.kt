package data.models

data class StationsDto(
    val id: Int,
    val name: String,
    val line: String,
    val order: Int,
    val is_transfer: Boolean,
    val transfer_lines: List<String>
)
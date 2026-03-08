package data.mapper

import data.models.StationsDto
import domain.models.MetroLine
import domain.models.Stations

object MetroMapper {
    fun toDomain(dto: StationsDto): Stations {
        return Stations(
            id = dto.id,
            name = dto.name,
            order = dto.order,
            isTransfer = dto.is_transfer,
            line = dto.line.toMetroLine(),
            transferLines = dto.transfer_lines.map { it.toMetroLine() },

            )
    }

    private fun String.toMetroLine(): MetroLine =
        when (this.trim().uppercase()) {
            "FIRST LINE"
                -> MetroLine.LINE_1

            "SECOND LINE"
                -> MetroLine.LINE_2

            "THIRD LINE"
                -> MetroLine.LINE_3

            else -> MetroLine.LINE_0

        }
}


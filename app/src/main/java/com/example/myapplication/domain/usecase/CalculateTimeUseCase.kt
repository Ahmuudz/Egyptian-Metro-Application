package domain.usecase

import domain.repo.MetroRepository

class CalculateTimeUseCase (val repo : MetroRepository) {
    operator fun invoke(count: Int) = count * repo.getTravelTime()
}
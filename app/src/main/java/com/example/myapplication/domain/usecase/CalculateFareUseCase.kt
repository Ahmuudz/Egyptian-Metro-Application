package domain.usecase

import domain.repo.MetroRepository

class CalculateFareUseCase (val repo : MetroRepository) {

    operator fun invoke (count : Int): Int{
        return when{
            count <= 9 -> 10
            count <= 20 -> 15
            else-> 20
        }
    }
}
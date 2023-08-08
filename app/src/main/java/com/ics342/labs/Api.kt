package com.ics342.labs

import java.util.UUID
import kotlin.random.Random

class Api {

    private val numbers: MutableList<Number>

    init {
        numbers = (1..20).map {
            Number(
                id = UUID.randomUUID().toString(),
                number = Random.nextInt()
            )
        }.toMutableList()
    }

    fun getNumbers(): List<Number> {
        return numbers.map { it }
    }

    fun getNumber(id: String): Number? {
        return numbers.firstOrNull { it.id == id }
    }

    fun addNumber(number: Number): Boolean {
        numbers.add(number)
        return true
    }
}

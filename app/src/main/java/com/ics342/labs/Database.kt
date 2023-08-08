package com.ics342.labs

class Database {

    private val numberStore: MutableList<Number> = mutableListOf()

    fun getAllNumbers(): List<Number> {
        return numberStore.map { it }
    }

    fun storeNumbers(numbers: List<Number>) {
        numberStore.clear()
        numberStore.addAll(numbers)
    }

    fun getNumber(id: String): Number? {
        return numberStore.firstOrNull { it.id == id }
    }
}

package com.ics342.labs

class NumbersRepository(private val database: Database, private val api: Api) {

    fun fetchNumbers(): List<Number> {
        val numbersInDatabase = database.getAllNumbers()
        return if (numbersInDatabase.isEmpty()) {
            val numbers = api.getNumbers()
            database.storeNumbers(numbers)
            numbers
        } else {
            numbersInDatabase
        }
    }

    fun getNumber(id: String): Number? {
        return database.getNumber(id) ?: api.getNumber(id)
    }
}

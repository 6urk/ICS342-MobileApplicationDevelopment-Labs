package com.ics342.labs
import io.mockk.*
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.util.UUID
import kotlin.random.Random

internal class NumbersRepositoryTest {

    @Test
    fun `If database does not have a number fetch it from the Api`() {
        // Setup
        val database = mockk<Database>()
        val api = mockk<Api>()
        val number = Number(UUID.randomUUID().toString(), Random.nextInt())
        val id = number.id

        every { database.getNumber(id) } returns null
        every { api.getNumber(id) } returns number

        // Act
        val repository = NumbersRepository(database, api)
        val result = repository.getNumber(id)

        // Assert
        assertNotNull(result)
        assertEquals(result, number)

        verify { database.getNumber(id) }
        verify { api.getNumber(id) }
    }

    @Test
    fun `fetchNumbers when database is empty should call api and store the numbers in the database`() {
        // Given
        val database = mockk<Database>()
        val api = mockk<Api>()
        val numbers = listOf(
            Number(UUID.randomUUID().toString(), Random.nextInt()),
            Number(UUID.randomUUID().toString(), Random.nextInt()),
            Number(UUID.randomUUID().toString(), Random.nextInt())
        )

        every { database.getAllNumbers() } returns emptyList()
        every { api.getNumbers() } returns numbers
        every { database.storeNumbers(numbers) } just Runs

        // When
        val repository = NumbersRepository(database, api)
        val result = repository.fetchNumbers()

        // Then
        assertEquals(numbers, result)
        verify(exactly = 1) { database.getAllNumbers() }
        verify(exactly = 1) { api.getNumbers() }
        verify(exactly = 1) { database.storeNumbers(numbers) }
    }

}

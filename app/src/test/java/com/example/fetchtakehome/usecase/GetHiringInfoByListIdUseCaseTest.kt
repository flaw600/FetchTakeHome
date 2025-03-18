package com.example.fetchtakehome.usecase

import com.example.fetchtakehome.model.HiringResponseItem
import com.example.fetchtakehome.repository.HiringRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetHiringInfoByListIdUseCaseTest {

    @MockK
    private lateinit var repository: HiringRepository
    private var hiringList = listOf(
        HiringResponseItem(1, 1, "Item 1"),
        HiringResponseItem(2, 1, "Item 2"),
        HiringResponseItem(3, 2, "Item 3"),
        HiringResponseItem(4, 2, "Item 4")
    )
    private lateinit var useCase: GetHiringInfoByListIdUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { repository.getHiringList() } coAnswers { hiringList }
        useCase = GetHiringInfoByListIdUseCase(repository)
    }

    @Test
    fun `Successful retrieval and processing of hiring list`() = runTest {
        val result = useCase.invoke()

        assertEquals(2, result.size)
        assertEquals(listOf(hiringList[0], hiringList[1]), result[1])
        assertEquals(listOf(hiringList[2], hiringList[3]), result[2])
    }

    @Test
    fun `Repository returns list with null or empty names`() = runTest {
        hiringList = listOf(
            HiringResponseItem(1, 1, "Item 1"),
            HiringResponseItem(2, 1, null),
            HiringResponseItem(3, 2, ""),
            HiringResponseItem(4, 2, "Item 4"),
        )

        val result = useCase.invoke()

        assertEquals(2, result.size)
        assertEquals(listOf(hiringList[0]), result[1])
        assertEquals(listOf(hiringList[3]), result[2])
    }

    @Test
    fun `Items within group are in correct order`() = runTest {
        hiringList = listOf(
            HiringResponseItem(1, 1, "Item C"),
            HiringResponseItem(2, 1, "Item A"),
            HiringResponseItem(3, 1, "Item B"),
            HiringResponseItem(4, 2, "Item F"),
            HiringResponseItem(5, 2, "Item D"),
            HiringResponseItem(6, 2, "Item E"),
        )

        val result = useCase.invoke()

        assertEquals(2, result.size)
        assertEquals(listOf(hiringList[1], hiringList[2], hiringList[0]), result[1])
        assertEquals(listOf(hiringList[4], hiringList[5], hiringList[3]), result[2])
    }

    @Test
    fun `Repository throws exception`() = runTest {
        coEvery { repository.getHiringList() } throws RuntimeException("Repository Error")

        try {
            useCase.invoke()
        } catch (e: RuntimeException) {
            assertEquals("Repository Error", e.message)
        }
    }
}
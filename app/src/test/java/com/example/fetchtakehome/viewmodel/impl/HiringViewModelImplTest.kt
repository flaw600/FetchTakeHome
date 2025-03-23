package com.example.fetchtakehome.viewmodel.impl

import android.util.Log
import com.example.fetchtakehome.R
import com.example.fetchtakehome.model.HiringCandidate
import com.example.fetchtakehome.usecase.GetHiringInfoByListIdUseCase
import com.example.fetchtakehome.viewmodel.HiringState
import com.example.fetchtakehome.viewmodel.HiringViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HiringViewModelImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockGetHiringInfoByListIdUseCase: GetHiringInfoByListIdUseCase = mockk()
    private lateinit var viewModel: HiringViewModel

    companion object {
        @JvmStatic
        @BeforeClass
        fun setupClass() {
            mockkStatic(Log::class)
        }
    }

    @Before
    fun setup() {
        every { Log.e(any(), any(), any()) } returns 0
        coEvery { mockGetHiringInfoByListIdUseCase.invoke() } coAnswers {
            delay(30L)
            mapOf(1 to listOf(HiringCandidate(id = 1, name = "Item 1", listId = 1)))
        }
        Dispatchers.setMain(testDispatcher)
        viewModel = HiringViewModelImpl(mockGetHiringInfoByListIdUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Initial state check`() = runTest(StandardTestDispatcher()) {
        val actualState = viewModel.hiringListState.first()
        assertEquals(HiringState.Loading, actualState)
    }

    @Test
    fun `Successful refresh updates state`() = runTest {
        val expectedData =
            mapOf(1 to listOf(HiringCandidate(id = 1, name = "Item 1", listId = 1)))
        coEvery { mockGetHiringInfoByListIdUseCase.invoke() } returns expectedData

        viewModel.refresh()

        val actualState = viewModel.hiringListState.value
        assertEquals(expectedData, (actualState as HiringState.Success).hiringListGroup)
    }

    @Test
    fun `Refresh with empty data shows the empty message`() = runTest {
        coEvery { mockGetHiringInfoByListIdUseCase.invoke() } returns emptyMap()

        viewModel.refresh()

        val actualState = viewModel.hiringListState.value
        assertEquals(R.string.empty_message, (actualState as HiringState.Message).messageResId)
    }

    @Test
    fun `Refresh throws exception`() = runTest {
        val expectedError = Exception("Test Exception")
        coEvery { mockGetHiringInfoByListIdUseCase.invoke() } throws expectedError

        viewModel.refresh()

        val actualState = viewModel.hiringListState.value
        assertEquals(R.string.error_message, (actualState as HiringState.Message).messageResId)
    }

    @Test
    fun `CancellationException handling`() = runTest {
        val expectedError = CancellationException("Test CancellationException")
        coEvery { mockGetHiringInfoByListIdUseCase.invoke() } throws expectedError

        viewModel.refresh()

        assertTrue(testDispatcher.isActive)
    }
}


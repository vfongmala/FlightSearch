package com.vichita.flightsearch.views.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vichita.flightsearch.R
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.utils.TestCoroutineRule
import com.vichita.flightsearch.views.data.SearchData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import java.lang.RuntimeException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ResultViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: FlightRepository

    @Mock
    private lateinit var context: Context

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: ResultViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ResultViewModel(repository, testDispatcher)
    }

    @Test
    fun testSearchWithSuccess() {
        testDispatcher.runBlockingTest {
            // Arrange
            // Prepare request
            val searchData = SearchData(
                "BKK",
                "MEL",
                "2021-12-01T00:00:00+07:00",
                "2021-12-31T00:00:00+07:00"
            )
            val channel = Channel<List<FlightInfo>>()
            val flow = channel.consumeAsFlow()
            whenever(repository.getSearchResult(searchData)).thenReturn(flow)

            // Prepare result
            val flightInfo = FlightInfo(
                "URL",
                "AirlineName",
                "12:00",
                "14:00",
                "id",
                1,
                99.99
            )
            val resultList = listOf(flightInfo)

            // Prepare search data
            val departing = Calendar.getInstance().apply {
                set(2021, 12-1, 1, 0, 0, 0)
            }
            val returning = Calendar.getInstance().apply {
                set(2021, 12-1, 31, 0, 0, 0)
            }
            whenever(context.getString(R.string.flight_result)).thenReturn("%s - %s : %s - %s")

            // Perform flow's emit event
            launch {
                channel.send(resultList)
            }

            // Act
            viewModel.setData(
                context,
                "BKK",
                "MEL",
                departing.timeInMillis,
                returning.timeInMillis)

            // Assert
            assertEquals(1, viewModel.result.value?.size)
            assertEquals(false, viewModel.showNoResult.value)
            assertEquals(false, viewModel.isLoading.value)
            assertEquals("BKK - MEL : 12/1/21 - 12/31/21", viewModel.searchCriteria.value)
        }
    }

    @Test
    fun testSearchWithNoResult() {
        testDispatcher.runBlockingTest {
            // Arrange
            // Prepare request
            val searchData = SearchData(
                "BKK",
                "MEL",
                "2021-12-01T00:00:00+07:00",
                "2021-12-31T00:00:00+07:00"
            )
            val channel = Channel<List<FlightInfo>>()
            val flow = channel.consumeAsFlow()
            whenever(repository.getSearchResult(searchData)).thenReturn(flow)

            // Prepare search data
            val departing = Calendar.getInstance().apply {
                set(2021, 12-1, 1, 0, 0, 0)
            }
            val returning = Calendar.getInstance().apply {
                set(2021, 12-1, 31, 0, 0, 0)
            }
            whenever(context.getString(R.string.flight_result)).thenReturn("%s - %s : %s - %s")

            // Perform flow's emit event with no result
            launch {
                channel.send(listOf())
            }

            // Act
            viewModel.setData(
                context,
                "BKK",
                "MEL",
                departing.timeInMillis,
                returning.timeInMillis)

            // Assert
            assertNull(null, viewModel.result.value)
            assertEquals(true, viewModel.showNoResult.value)
            assertEquals(false, viewModel.isLoading.value)
            assertEquals("BKK - MEL : 12/1/21 - 12/31/21", viewModel.searchCriteria.value)
        }
    }

    @Test
    fun testSearchWithError() {
        testDispatcher.runBlockingTest {
            // Arrange
            // Prepare request
            val searchData = SearchData(
                "BKK",
                "MEL",
                "2021-12-01T00:00:00+07:00",
                "2021-12-31T00:00:00+07:00"
            )
            whenever(repository.getSearchResult(searchData)) doReturn flow { throw Exception()}

            // Prepare search data
            val departing = Calendar.getInstance().apply {
                set(2021, 12-1, 1, 0, 0, 0)
            }
            val returning = Calendar.getInstance().apply {
                set(2021, 12-1, 31, 0, 0, 0)
            }
            whenever(context.getString(R.string.flight_result)).thenReturn("%s - %s : %s - %s")


            // Act
            viewModel.setData(
                context,
                "BKK",
                "MEL",
                departing.timeInMillis,
                returning.timeInMillis)

            // Assert
            assertNull(null, viewModel.result.value)
            assertEquals(true, viewModel.showNoResult.value)
            assertEquals(false, viewModel.isLoading.value)
            assertEquals("BKK - MEL : 12/1/21 - 12/31/21", viewModel.searchCriteria.value)
        }
    }

    @Test
    fun testSearchWithNullData() {
        // Act
        viewModel.setData(
            context,
            null,
            null,
            null,
            null)

        // Assert
        assertNull(null, viewModel.result.value)
        assertEquals(true, viewModel.showNoResult.value)
        assertEquals(null, viewModel.isLoading.value)
        assertEquals(null, viewModel.searchCriteria.value)
    }
}
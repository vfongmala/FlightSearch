package com.vichita.flightsearch.views.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.utils.TestCoroutineRule
import com.vichita.flightsearch.views.data.SearchData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ResultViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: FlightRepository

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSearchWithSuccess() {
        testDispatcher.runBlockingTest {
            val flightInfo = FlightInfo(
                "URL",
                "AirlineName",
                "12:00",
                "14:00",
                "id",
                1,
                99.99
            )
            val searchData = SearchData(
                "BKK",
                "MEL",
                "2012-12-24T00:00:00+07:00",
                "2012-12-24T00:00:00+07:00"
            )

            val resultList = listOf(flightInfo)


            val viewModel = ResultViewModel(repository, testDispatcher)
            val channel = Channel<List<FlightInfo>>()
            val flow = channel.consumeAsFlow()

            whenever(repository.getSearchResult(searchData)).thenReturn(flow)

            launch {
                channel.send(resultList)
            }
            viewModel.search(searchData)

            Assert.assertEquals(1, viewModel.result.value?.size)
        }
    }
}
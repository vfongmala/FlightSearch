package com.vichita.flightsearch.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vichita.flightsearch.api.FlightAPI
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.utils.TestCoroutineRule
import com.vichita.flightsearch.views.data.SearchData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class FlightRepositoryTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var api: FlightAPI

    private lateinit var repository: FlightRepository

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = FlightRepository(api)
    }

//    @Test
//    fun testGetSearchResultWithSuccess() {
//        val searchData = SearchData(
//            "BKK",
//            "MEL",
//            "2012-12-24T00:00:00+07:00",
//            "2012-12-24T00:00:00+07:00"
//        )
//        runBlocking {
//            whenever(api.searchFlights(
//                searchData.departure,
//                searchData.arrival,
//                searchData.departureDate,
//                searchData.returnDate
//            )).thenReturn(listOf())
//        }
//
//        repository.getSearchResult(searchData)
//    }

    @Test
    fun testGetSearchResultWithSuccess() {
        testDispatcher.runBlockingTest {
            val searchData = SearchData(
                "BKK",
                "MEL",
                "2012-12-24T00:00:00+07:00",
                "2012-12-24T00:00:00+07:00"
            )
            val channel = Channel<List<FlightInfo>>()
            val flow = channel.consumeAsFlow()

            whenever(api.searchFlights(
                searchData.departure,
                searchData.arrival,
                searchData.departureDate,
                searchData.returnDate
            )).thenReturn(listOf())

            launch {
                val list = repository.getSearchResult(searchData)
            }
        }
    }
}
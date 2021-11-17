package com.vichita.flightsearch.views.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.util.Pair
import com.vichita.flightsearch.views.data.SearchData
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.*

class SearchViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    private val viewModel = SearchViewModel()

    @Test
    fun testSelectDate() {
        // Arrange
        val departing = Calendar.getInstance().apply {
            set(2021, 12-1, 1)
        }
        val returning = Calendar.getInstance().apply {
            set(2021, 12-1, 31)
        }

        // Act
        viewModel.selectDates(Pair(departing.timeInMillis, returning.timeInMillis))

        // Assert
        assertEquals("12/1/21", viewModel.departingDate.value)
        assertEquals("12/31/21", viewModel.returningDate.value)
    }

    @Test
    fun testPerformSearchWithSuccess() {
        // Arrange
        val departing = Calendar.getInstance().apply {
            set(2021, 12-1, 1, 0, 0, 0)
        }
        val returning = Calendar.getInstance().apply {
            set(2021, 12-1, 31, 0, 0, 0)
        }
        // Set departing and returning dates, mocking calendar behaviour
        viewModel.selectDates(Pair(departing.timeInMillis, returning.timeInMillis))

        // Act
        val actualSearchData = viewModel.performSearch("BKK", "MEL")

        // Assert
        val expectedSearchData = SearchData(
            "BKK", "MEL", "2021-12-01T00:00:00+07:00","2021-12-31T00:00:00+07:00"
        )
        assertNotNull(actualSearchData)
        assertEquals(expectedSearchData, actualSearchData)
        assertEquals(true, viewModel.departureAirportValid.value)
        assertEquals(true, viewModel.arrivalAirportValid.value)
        assertEquals(true, viewModel.departingValid.value)
        assertEquals(true, viewModel.returningValid.value)
    }

    @Test
    fun testPerformSearchWithoutSearchDetails() {
        // Act
        val actualSearchData = viewModel.performSearch("", "")

        // Assert
        assertNull(actualSearchData)
        assertEquals(false, viewModel.departureAirportValid.value)
        assertEquals(false, viewModel.arrivalAirportValid.value)
        assertEquals(false, viewModel.departingValid.value)
        assertEquals(false, viewModel.returningValid.value)
    }
}
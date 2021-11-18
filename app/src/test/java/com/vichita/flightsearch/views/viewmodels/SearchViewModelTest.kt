package com.vichita.flightsearch.views.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.util.Pair
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
        viewModel.performSearch("BKK", "MEL")

        assertNotNull("BKK", viewModel.departure)
        assertEquals("MEL", viewModel.arrival)
        assertEquals(true, viewModel.searchValid.value)
    }

    @Test
    fun testPerformSearchWithoutSearchDetails() {
        // Act
        viewModel.performSearch("", "")

        // Assert
        assertEquals(false, viewModel.searchValid.value)
    }
}
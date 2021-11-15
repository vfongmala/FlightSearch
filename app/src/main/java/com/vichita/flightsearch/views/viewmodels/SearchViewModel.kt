package com.vichita.flightsearch.views.viewmodels

import androidx.core.util.Pair
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
    var selectedDates: Pair<Long, Long>? = null

    var departingDate = MutableLiveData<String>()
    var returningDate = MutableLiveData<String>()

    private val displayingDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD)
    fun selectDates(dateRange: Pair<Long, Long>) {
        selectedDates = dateRange

        val departing = Date(dateRange.first)
        val returning = Date(dateRange.second)

        departingDate.value = displayingDateFormat.format(departing)
        returningDate.value = displayingDateFormat.format(returning)
    }
}
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

    var departingDate = MutableLiveData<String>()
    var returningDate = MutableLiveData<String>()
    var searchValid = MutableLiveData<Boolean>()

    var departure: String? = null
    var arrival: String? = null
    var selectedDates: Pair<Long, Long>? = null

    private val displayingDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD, Locale.ENGLISH)

    fun selectDates(dateRange: Pair<Long, Long>) {
        selectedDates = dateRange

        val departing = Date(dateRange.first)
        val returning = Date(dateRange.second)

        departingDate.postValue(displayingDateFormat.format(departing))
        returningDate.postValue(displayingDateFormat.format(returning))
    }

    fun performSearch(
        departureAirport: String,
        arrivalAirport: String
    ) {
        if (departureAirport.isNotEmpty() && arrivalAirport.isNotEmpty() && selectedDates != null) {
            departure = departureAirport
            arrival = arrivalAirport
            searchValid.postValue(true)
        } else {
            searchValid.postValue(false)
        }
    }

    fun resetSearch() {
        searchValid.postValue(false)
    }
}
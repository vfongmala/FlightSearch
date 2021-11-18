package com.vichita.flightsearch.views.viewmodels

import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {

    private var _departingDate = MutableLiveData<String>()
    val departingDate: LiveData<String> = _departingDate

    private var _returningDate = MutableLiveData<String>()
    val returningDate: LiveData<String> = _returningDate

    private var _searchValid = MutableLiveData<Boolean>()
    val searchValid: LiveData<Boolean> = _searchValid

    var departure: String? = null
    var arrival: String? = null
    var selectedDates: Pair<Long, Long>? = null

    private val displayingDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD, Locale.ENGLISH)

    fun selectDates(dateRange: Pair<Long, Long>) {
        selectedDates = dateRange

        val departing = Date(dateRange.first)
        val returning = Date(dateRange.second)

        _departingDate.postValue(displayingDateFormat.format(departing))
        _returningDate.postValue(displayingDateFormat.format(returning))
    }

    fun performSearch(
        departureAirport: String,
        arrivalAirport: String
    ) {
        if (departureAirport.isNotEmpty() && arrivalAirport.isNotEmpty() && selectedDates != null) {
            departure = departureAirport
            arrival = arrivalAirport
            _searchValid.postValue(true)
        } else {
            _searchValid.postValue(false)
        }
    }

    fun resetSearch() {
        _searchValid.postValue(false)
    }
}
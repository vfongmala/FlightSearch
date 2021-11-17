package com.vichita.flightsearch.views.viewmodels

import androidx.core.util.Pair
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vichita.flightsearch.views.data.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
    var selectedDates: Pair<Long, Long>? = null

    var departingDate = MutableLiveData<String>()
    var returningDate = MutableLiveData<String>()

    var departureAirportValid = MutableLiveData<Boolean>()
    var arrivalAirportValid = MutableLiveData<Boolean>()
    var departingValid = MutableLiveData<Boolean>()
    var returningValid = MutableLiveData<Boolean>()

    private val displayingDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD, Locale.ENGLISH)
    private val requestDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH)

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
    ): SearchData? {
        departureAirportValid.value = departureAirport.isNotEmpty()
        arrivalAirportValid.value = arrivalAirport.isNotEmpty()
        departingValid.value = selectedDates != null
        returningValid.value = selectedDates != null

        if (departureAirportValid.value == false || arrivalAirportValid.value == false ||
            departingValid.value == false || returningValid.value == false
        ) {
            return null
        }

        val departing = Date(selectedDates!!.first)
        val returning = Date(selectedDates!!.second)
        return SearchData(
            departureAirport,
            arrivalAirport,
            requestDateFormat.format(departing),
            requestDateFormat.format(returning)
        )
    }
}
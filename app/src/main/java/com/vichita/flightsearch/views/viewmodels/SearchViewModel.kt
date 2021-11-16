package com.vichita.flightsearch.views.viewmodels

import android.os.Bundle
import androidx.core.util.Pair
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vichita.flightsearch.constants.Constant
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

    private val displayingDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD)
    private val requestDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.XXX")

    fun selectDates(dateRange: Pair<Long, Long>) {
        selectedDates = dateRange

        val departing = Date(dateRange.first)
        val returning = Date(dateRange.second)

        departingDate.value = displayingDateFormat.format(departing)
        returningDate.value = displayingDateFormat.format(returning)
    }

    fun performSearch(
        departureAirport: String,
        arrivalAirport: String
    ): Bundle? {
        departureAirportValid.value = departureAirport.isNotEmpty()
        arrivalAirportValid.value = arrivalAirport.isNotEmpty()
        departingValid.value = selectedDates != null
        returningValid.value = selectedDates != null

        if (departureAirportValid.value == false || arrivalAirportValid.value == false ||
            departingValid.value == false || returningValid.value == false) {
            return null
        }

        val departing = Date(selectedDates!!.first)
        val returning = Date(selectedDates!!.second)
        val searchData = SearchData(
            departureAirport,
            arrivalAirport,
            requestDateFormat.format(departing),
            requestDateFormat.format(returning)
        )
        val bundle = Bundle()
        bundle.putParcelable(Constant.SEARCH_DATA_KEY, searchData)

        return bundle
    }
}
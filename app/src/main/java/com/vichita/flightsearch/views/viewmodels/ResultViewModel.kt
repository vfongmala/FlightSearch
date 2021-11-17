package com.vichita.flightsearch.views.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vichita.flightsearch.R
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.views.data.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: FlightRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _result = MutableLiveData<List<FlightInfo>>()
    val result: LiveData<List<FlightInfo>> = _result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _showNoResult = MutableLiveData<Boolean>()
    val showNoResult: LiveData<Boolean> = _showNoResult

    private val _searchCriteria = MutableLiveData<String>()
    val searchCriteria: LiveData<String> = _searchCriteria

    private lateinit var departure: String
    private lateinit var arrival: String
    private lateinit var departing: Date
    private lateinit var returning: Date

    private val displayingDateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.DATE_FIELD, Locale.ENGLISH)
    private val requestDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH)

    init {
        _isLoading.postValue(false)
        _showNoResult.postValue(false)
    }

    fun setData(context: Context, departure: String?, arrival: String?, departing: Long?, returning: Long?) {
        if (departure != null && arrival != null && departing != null && returning != null) {
            this.departure = departure
            this.arrival = arrival
            this.departing = Date(departing)
            this.returning = Date(returning)

            search()
            composeSearchCriteria(context)
        } else {
            _showNoResult.postValue(false)
        }
    }

    private fun search() {
        val data = SearchData(
            departure,
            arrival,
            requestDateFormat.format(departing),
            requestDateFormat.format(returning)
        )
        viewModelScope.launch(dispatcher) {
            _isLoading.postValue(true)
            try {
                repository.getSearchResult(data).collect {
                    if (it.isNotEmpty()) {
                        _result.postValue(it)
                    } else {
                        _showNoResult.postValue(true)
                    }
                    _isLoading.postValue(false)
                }
            } catch (e: Exception) {
                _isLoading.postValue(false)
                _showNoResult.postValue(true)
                e.printStackTrace()
            }
        }
    }

    private fun composeSearchCriteria(context: Context) {
        val searchCriteria = String.format(
            context.getString(R.string.flight_result),
            departure,
            arrival,
            displayingDateFormat.format(departing),
            displayingDateFormat.format(returning)
        )

        _searchCriteria.postValue(searchCriteria)
    }
}
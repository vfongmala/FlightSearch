package com.vichita.flightsearch.views.viewmodels

import androidx.lifecycle.ViewModel
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.views.data.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: FlightRepository
): ViewModel() {

    private var result: Flow<List<FlightInfo>>? = null

    fun fetchData(data: SearchData): Flow<List<FlightInfo>> {
        val newResult = repository.getSearchResult(data)
        result = newResult
        return newResult
    }
}
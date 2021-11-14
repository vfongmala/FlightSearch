package com.vichita.flightsearch.views.viewmodels

import androidx.lifecycle.*
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.views.data.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: FlightRepository,
): ViewModel() {

    var result: Flow<List<FlightInfo>>? = null
    var isLoading = MutableLiveData<Boolean>()

    fun fetchData(data: SearchData): Flow<List<FlightInfo>> {
        isLoading.value = true

        val newResult = repository.getSearchResult(data)
            .onCompletion {
                isLoading.value = false
            }
            .flowOn(Dispatchers.IO)
            .catch {
                isLoading.value = false
            }
        result = newResult
        return newResult
    }
}
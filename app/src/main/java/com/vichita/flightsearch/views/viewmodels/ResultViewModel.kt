package com.vichita.flightsearch.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.views.data.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: FlightRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _result = MutableLiveData<List<FlightInfo>>()
    val result: LiveData<List<FlightInfo>> = _result

    var isLoading = MutableLiveData<Boolean>()

    init {
        isLoading.postValue(true)
    }

    fun search(data: SearchData) {
        viewModelScope.launch(dispatcher) {
            isLoading.postValue(true)
            try {
                repository.getSearchResult(data).collect {
                    _result.postValue(it)
                    isLoading.postValue(false)
                }
            } catch (e: Exception) {
                isLoading.postValue(false)
                e.printStackTrace()
            }
        }
    }
}
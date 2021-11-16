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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val _showNoResult = MutableLiveData<Boolean>()
    val showNoResult: LiveData<Boolean> = _showNoResult

    init {
        _isLoading.postValue(true)
        _showNoResult.postValue(false)
    }

    fun search(data: SearchData) {
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
}
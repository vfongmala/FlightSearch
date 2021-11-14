package com.vichita.flightsearch.views.viewmodels

import androidx.lifecycle.*
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.views.data.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: FlightRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchData: MutableStateFlow<SearchData?> = MutableStateFlow(savedStateHandle.get("search_data"))

    var result: LiveData<List<FlightInfo>> = searchData.flatMapLatest {
        repository.getSearchResult(it)
    }.asLiveData()

    var isLoading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            searchData.collect {
                savedStateHandle.set("search_data", it)
            }
        }
    }

    fun setSearchData(data: SearchData) {
        searchData.value = data
    }
}
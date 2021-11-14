package com.vichita.flightsearch.views.viewmodels

import androidx.lifecycle.*
import com.vichita.flightsearch.constants.Constant
import com.vichita.flightsearch.data.FlightRepository
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.views.data.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: FlightRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchData: MutableStateFlow<SearchData?> =
        MutableStateFlow(savedStateHandle.get(Constant.SEARCH_DATA_KEY))

    var result: LiveData<List<FlightInfo>> = searchData.flatMapLatest {
        isLoading.value = true
        repository.getSearchResult(it)
            .onCompletion {
                isLoading.value = false
            }.catch {
                isLoading.value = false
            }
    }.asLiveData()

    var isLoading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            searchData.collect {
                savedStateHandle.set(Constant.SEARCH_DATA_KEY, it)
            }
        }
    }

    fun setSearchData(data: SearchData) {
        searchData.value = data
    }
}
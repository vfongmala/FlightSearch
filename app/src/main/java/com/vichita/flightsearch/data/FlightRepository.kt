package com.vichita.flightsearch.data

import com.vichita.flightsearch.api.FlightAPI
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.views.data.SearchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FlightRepository @Inject constructor(private val api: FlightAPI) {

    suspend fun getSearchResult(searchData: SearchData): Flow<List<FlightInfo>> {
        return flow {
            searchData.let {
                val results = api.searchFlights(
                    searchData.departure,
                    searchData.arrival,
                    searchData.departureDate,
                    searchData.returnDate
                )
                emit(results)
            }
        }.flowOn(Dispatchers.IO)
    }

}
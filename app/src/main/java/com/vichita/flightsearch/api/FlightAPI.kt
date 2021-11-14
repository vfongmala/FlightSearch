package com.vichita.flightsearch.api

import com.vichita.flightsearch.entity.FlightInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightAPI {
    @GET("Flight")
    suspend fun searchFlights(
        @Query("DepartureAirportCode") departureAirportCode: String,
        @Query("ArrivalAirportCode") arrivalAirportCode: String,
        @Query("DepartureDate") departureDate: String,
        @Query("ArrivalDate") arrivalDate: String,
    ): List<FlightInfo>
}
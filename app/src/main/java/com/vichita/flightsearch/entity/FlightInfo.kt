package com.vichita.flightsearch.entity

import com.google.gson.annotations.SerializedName

data class FlightInfo(
    @SerializedName("AirlineLogoAddress") val logo: String,
    @SerializedName("AirlineName") val name: String,
    @SerializedName("InboundFlightsDuration") val inboundDuration: String,
    @SerializedName("OutboundFlightsDuration") val outboundDuration: String,
    @SerializedName("ItineraryId") val itineraryId: String,
    @SerializedName("Stops") val stops: Int,
    @SerializedName("TotalAmount") val amount: Double
)

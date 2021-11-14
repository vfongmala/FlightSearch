package com.vichita.flightsearch.views.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchData(
    val departure: String,
    val arrival: String,
    val departureDate: String,
    val returnDate: String,
): Parcelable
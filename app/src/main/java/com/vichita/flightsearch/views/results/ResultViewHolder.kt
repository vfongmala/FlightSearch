package com.vichita.flightsearch.views.results

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vichita.flightsearch.databinding.ItemListResultBinding

class ResultViewHolder(binding: ItemListResultBinding): RecyclerView.ViewHolder(binding.root) {
    val itemIconView: ImageView = binding.itemIcon
    val itemNameView: TextView = binding.itemName
    val itemOutboundView: TextView = binding.itemOutbound
    val itemInboundView: TextView = binding.itemInbound
    val itemAmount: TextView = binding.itemAmount
}
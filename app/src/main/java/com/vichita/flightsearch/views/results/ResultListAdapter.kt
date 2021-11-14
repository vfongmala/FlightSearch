package com.vichita.flightsearch.views.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vichita.flightsearch.entity.FlightInfo
import com.vichita.flightsearch.databinding.ItemListResultBinding

class ResultListAdapter: RecyclerView.Adapter<ResultViewHolder>() {

    private var resultList: MutableList<FlightInfo> = mutableListOf()

    fun setData(newData: List<FlightInfo>) {
        resultList.clear()
        resultList.addAll(newData)
        notifyItemRangeChanged(0, newData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        // bind data
        val data = resultList[position]
        holder.itemNameView.text = data.name
        holder.itemOutboundView.text = data.outboundDuration
        holder.itemInboundView.text = data.inboundDuration
        holder.itemAmount.text = data.amount.toString()
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}
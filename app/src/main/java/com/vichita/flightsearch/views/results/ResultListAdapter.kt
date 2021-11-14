package com.vichita.flightsearch.views.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vichita.flightsearch.databinding.ItemListResultBinding

class ResultListAdapter: RecyclerView.Adapter<ResultViewHolder>() {

    private var data: MutableList<String> = mutableListOf()

    fun setData(newData: List<String>) {
        data.clear()
        data.addAll(newData)
        notifyItemRangeChanged(0, newData.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemListResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        // bind data
        holder.itemNameView.text= data[position]
        holder.itemOutboundView.text= data[position]
        holder.itemInboundView.text= data[position]
        holder.itemAmount.text= data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
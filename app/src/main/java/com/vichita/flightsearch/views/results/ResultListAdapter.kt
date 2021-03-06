package com.vichita.flightsearch.views.results

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vichita.flightsearch.R
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
        if (data.logo.isNotEmpty()) {
            val uri = Uri.parse(data.logo)
            Glide.with(holder.itemView.context)
                .asGif()
                .load(uri)
                .into(holder.itemIconView)
        } else {
            holder.itemIconView.visibility = View.GONE
        }
        holder.itemNameView.text = data.name
        holder.itemOutboundView.apply {
            text = context.getString(R.string.outbound_duration, data.outboundDuration)
        }
        holder.itemInboundView.apply {
            text = context.getString(R.string.inbound_duration, data.inboundDuration)
        }
        holder.itemAmount.apply {
            text = context.getString(R.string.amount, data.amount)
        }
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}
package com.vichita.flightsearch.views.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vichita.flightsearch.databinding.FragmentResultListBinding
import com.vichita.flightsearch.views.data.SearchData
import com.vichita.flightsearch.views.results.ResultListAdapter

class ResultFragment: Fragment() {
    private var _binding: FragmentResultListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ResultListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.resultList

        adapter = ResultListAdapter()
        recyclerView.adapter = adapter

        fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchData() {
        val data: SearchData? = arguments?.getParcelable("search_data")
        if (data != null) {
            adapter.setData(listOf("test", "test2"))
        } else {
            adapter.setData(listOf("no data"))
        }
    }
}
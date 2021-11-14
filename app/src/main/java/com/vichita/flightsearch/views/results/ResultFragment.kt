package com.vichita.flightsearch.views.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.vichita.flightsearch.databinding.FragmentResultListBinding
import com.vichita.flightsearch.views.data.SearchData
import com.vichita.flightsearch.views.viewmodels.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment: Fragment() {
    private var _binding: FragmentResultListBinding? = null
    private val binding get() = _binding!!

    private var searchJob: Job? = null
    private val viewModel: ResultViewModel by viewModels()

    private lateinit var adapter: ResultListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultListBinding.inflate(inflater, container, false)
        adapter = ResultListAdapter()

        binding.resultList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchData() {
        searchJob?.cancel()
        val data: SearchData? = arguments?.getParcelable("search_data")
        if (data != null) {
            searchJob = lifecycleScope.launch {
                viewModel.fetchData(data).collect {
                    adapter.setData(it)
                }
            }
        }
    }
}
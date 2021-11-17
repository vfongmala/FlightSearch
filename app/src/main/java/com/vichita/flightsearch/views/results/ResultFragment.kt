package com.vichita.flightsearch.views.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vichita.flightsearch.constants.Constant
import com.vichita.flightsearch.databinding.FragmentResultListBinding
import com.vichita.flightsearch.views.viewmodels.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: FragmentResultListBinding? = null
    private val binding get() = _binding!!

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

        bindViewModel()
        setData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData() {
        viewModel.setData(
            requireContext(),
            arguments?.getString(Constant.SEARCH_DEPARTURE),
            arguments?.getString(Constant.SEARCH_ARRIVAL),
            arguments?.getLong(Constant.SEARCH_DEPARTING),
            arguments?.getLong(Constant.SEARCH_RETURNING),
        )
    }

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.resultLoading.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.showNoResult.observe(viewLifecycleOwner, {
            binding.noResult.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.result.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })

        viewModel.searchCriteria.observe(viewLifecycleOwner) {
            binding.resultTitle.text = it
        }
    }
}
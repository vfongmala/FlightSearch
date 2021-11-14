package com.vichita.flightsearch.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vichita.flightsearch.R
import com.vichita.flightsearch.databinding.FragmentSearchBinding
import com.vichita.flightsearch.views.data.SearchData

class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.searchBtn.setOnClickListener {
            val resultFragmentContainer: View? = view.findViewById(R.id.search_result_nav_container)
            if (resultFragmentContainer == null) {
                navigateToResultList(true, view)
            } else {
                navigateToResultList(false, resultFragmentContainer)
            }
        }
    }

    private fun navigateToResultList(isOnPhone: Boolean, view: View) {
        val searchData = SearchData(
            binding.searchView.departureCodeEdt.toString(),
            binding.searchView.arrivalCodeEdt.toString(),
            binding.searchView.departingEdt.toString(),
            binding.searchView.returningEdt.toString()
        )
        val bundle = Bundle()
        bundle.putParcelable("search_data", searchData)
        if (isOnPhone) {
            view.findNavController().navigate(R.id.show_result_list, bundle)
        } else {
            view.findNavController().navigate(R.id.fragment_result, bundle)
        }
    }
}
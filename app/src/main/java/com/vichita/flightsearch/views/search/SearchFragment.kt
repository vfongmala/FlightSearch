package com.vichita.flightsearch.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.vichita.flightsearch.R
import com.vichita.flightsearch.constants.Constant
import com.vichita.flightsearch.databinding.FragmentSearchBinding
import com.vichita.flightsearch.views.data.SearchData
import com.vichita.flightsearch.views.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

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

        binding.searchView.departingEdt.setOnClickListener {
            showDatePicker()
        }

        binding.searchView.returningEdt.setOnClickListener {
            showDatePicker()
        }

        viewModel.departingDate.observe(viewLifecycleOwner) {
            binding.searchView.departingEdt.setText(it)
        }

        viewModel.returningDate.observe(viewLifecycleOwner) {
            binding.searchView.returningEdt.setText(it)
        }
    }

    private fun navigateToResultList(isOnPhone: Boolean, view: View) {
        val searchData = SearchData(
            binding.searchView.departureCodeEdt.text.toString(),
            binding.searchView.arrivalCodeEdt.text.toString(),
            binding.searchView.departingEdt.text.toString(),
            binding.searchView.returningEdt.text.toString()
        )
        val bundle = Bundle()
        bundle.putParcelable(Constant.SEARCH_DATA_KEY, searchData)
        if (isOnPhone) {
            view.findNavController().navigate(R.id.show_result_list, bundle)
        } else {
            view.findNavController().navigate(R.id.fragment_result, bundle)
        }
    }

    private fun showDatePicker() {
        val calendarConstraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.dateRangePicker().apply {
            setTitleText(R.string.departing)
            setCalendarConstraints(calendarConstraints.build())
            viewModel.selectedDates?.let {
                setSelection(it)
            }
        }.build()

        datePicker.addOnPositiveButtonClickListener {
            viewModel.selectDates(it)
        }
        datePicker.show(parentFragmentManager, "departing")
    }
}
package com.example.myapplication.presintation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels { ViewModelFactory(requireContext()) }

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

        setupObservers()

        binding.btnFindRoute.setOnClickListener {
            val start = binding.atvStart.text.toString()
            val end = binding.atvEnd.text.toString()
            
            if (start.isNotBlank() && end.isNotBlank()) {
                val action = SearchFragmentDirections.actionSearchFragmentToRouteFragment(start, end)
                findNavController().navigate(action)
            } else {
                if (start.isBlank()) binding.tilStart.error = "Select start station"
                if (end.isBlank()) binding.tilEnd.error = "Select end station"
            }
        }
    }

    private fun setupObservers() {
        viewModel.stations.observe(viewLifecycleOwner) { stations ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, stations)
            binding.atvStart.setAdapter(adapter)
            binding.atvEnd.setAdapter(adapter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.myapplication.presintation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentRouteBinding
import domain.models.RouteResult

class RouteFragment : Fragment() {

    private var _binding: FragmentRouteBinding? = null
    private val binding get() = _binding!!

    private val args: RouteFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var adapter: RouteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        viewModel.findRoute(args.startStation, args.endStation)
    }

    private fun setupRecyclerView() {
        // In a real app, inject this
        val repository = data.repo.MetroRepositoryImpl(data.dataSource.MetroJsonDataSource(requireContext()))
        val getFirstStationUseCase = domain.usecase.GetFirstStationUseCase(repository)
        val getLastStationUseCase = domain.usecase.GetLastStationUseCase(repository)
        val directionUseCase = domain.usecase.GetDirectionUseCase(getFirstStationUseCase, getLastStationUseCase)

        adapter = RouteAdapter(directionUseCase)
        binding.rvRoute.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRoute.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.routeResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RouteResult.Success -> {
                    binding.tvSummary.text = "Fare: ${result.fare} EGP | Time: ${result.time} min"
                    binding.tvStats.text = "Total Stations: ${result.stations.distinctBy { it.name }.size}"
                    adapter.submitList(result.stations)
                }
                is RouteResult.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

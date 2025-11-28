package com.example.projet_couvoiturage.ui.employee.trips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projet_couvoiturage.databinding.FragmentTripListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripListFragment : Fragment() {

    private var _binding: FragmentTripListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TripListViewModel by viewModels()
    private lateinit var adapter: TripAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTripListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TripAdapter { trip ->
            val action = TripListFragmentDirections.actionTripListFragmentToTripDetailFragment(trip.id)
            findNavController().navigate(action)
        }
        
        binding.rvTrips.layoutManager = LinearLayoutManager(context)
        binding.rvTrips.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val dep = binding.etDepCity.text.toString().takeIf { it.isNotBlank() }
            val arr = binding.etArrCity.text.toString().takeIf { it.isNotBlank() }
            viewModel.search(dep, arr, null)
        }

        viewModel.trips.observe(viewLifecycleOwner) { trips ->
            adapter.submitList(trips)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

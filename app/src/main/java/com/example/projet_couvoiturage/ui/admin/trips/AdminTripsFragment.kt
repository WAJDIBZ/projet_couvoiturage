package com.example.projet_couvoiturage.ui.admin.trips

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projet_couvoiturage.databinding.FragmentAdminTripsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminTripsFragment : Fragment() {

    private var _binding: FragmentAdminTripsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AdminTripsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminTripsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val adapter = AdminTripAdapter { trip ->
            AlertDialog.Builder(requireContext())
                .setTitle("Cancel Trip")
                .setMessage("Are you sure you want to cancel trip ${trip.departureCity} -> ${trip.arrivalCity}?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.cancelTrip(trip.id)
                }
                .setNegativeButton("No", null)
                .show()
        }
        
        binding.rvAdminTrips.layoutManager = LinearLayoutManager(context)
        binding.rvAdminTrips.adapter = adapter
        
        viewModel.trips.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

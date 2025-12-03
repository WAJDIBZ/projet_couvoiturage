package com.example.projet_couvoiturage.ui.employee.detail

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projet_couvoiturage.databinding.FragmentTripDetailBinding
import com.example.projet_couvoiturage.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import javax.inject.Inject
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import com.example.projet_couvoiturage.ui.employee.workers.TripReminderWorker
import com.example.projet_couvoiturage.ui.employee.workers.TripUpdateWorker

@AndroidEntryPoint
class TripDetailFragment : Fragment() {

    private var _binding: FragmentTripDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TripDetailViewModel by viewModels()
    private val args: TripDetailFragmentArgs by navArgs()
    
    @Inject lateinit var notificationHelper: NotificationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // OSM Configuration
        val ctx = requireContext()
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        
        _binding = FragmentTripDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tripId = args.tripId
        
        // Setup Map
        binding.mapView.setTileSource(TileSourceFactory.MAPNIK)
        binding.mapView.setMultiTouchControls(true)
        val mapController = binding.mapView.controller
        mapController.setZoom(9.0)

        viewModel.getTrip(tripId).observe(viewLifecycleOwner) { trip ->
            if (trip != null) {
                binding.tvDetailRoute.text = "${trip.departureCity} -> ${trip.arrivalCity}"
                binding.tvDetailDate.text = "${trip.date} at ${trip.departureTime}"
                binding.tvDetailPrice.text = "${trip.pricePerSeat} TND"
                binding.tvDetailSeats.text = "${trip.seatsAvailable} seats available"
                binding.tvDetailNotes.text = trip.notes

                // Map Markers
                val startPoint = GeoPoint(trip.departureLat, trip.departureLng)
                val endPoint = GeoPoint(trip.arrivalLat, trip.arrivalLng)
                
                mapController.setCenter(startPoint)

                val startMarker = Marker(binding.mapView)
                startMarker.position = startPoint
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                startMarker.title = trip.departureCity
                binding.mapView.overlays.add(startMarker)

                val endMarker = Marker(binding.mapView)
                endMarker.position = endPoint
                endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                endMarker.title = trip.arrivalCity
                binding.mapView.overlays.add(endMarker)
                
                // Real Route using OSRMRoadManager
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val roadManager = OSRMRoadManager(requireContext(), "CovoiturageApp/1.0")
                        val waypoints = ArrayList<GeoPoint>()
                        waypoints.add(startPoint)
                        waypoints.add(endPoint)
                        val road = roadManager.getRoad(waypoints)
                        
                        if (road.mStatus == Road.STATUS_OK) {
                            val roadOverlay = RoadManager.buildRoadOverlay(road)
                            withContext(Dispatchers.Main) {
                                binding.mapView.overlays.add(roadOverlay)
                                binding.mapView.invalidate()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                
                binding.mapView.invalidate()

                binding.btnBook.isEnabled = trip.seatsAvailable > 0
                binding.btnBook.setOnClickListener {
                    val userId = com.example.projet_couvoiturage.auth.SessionManager.currentUserId
                    if (userId != null) {
                        viewModel.bookTrip(trip.id, userId)
                    } else {
                        Toast.makeText(requireContext(), "Please log in to book", Toast.LENGTH_SHORT).show()
                    }
                }
                
                binding.btnChat.setOnClickListener {
                    val action = TripDetailFragmentDirections.actionTripDetailFragmentToChatFragment(trip.id)
                    findNavController().navigate(action)
                }
            }
        }

        viewModel.reservationStatus.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(context, "Reservation Confirmed!", Toast.LENGTH_SHORT).show()
                notificationHelper.showNotification(1, "Reservation Confirmed", "Your seat has been booked.")
                
                // Schedule Reminder (10 seconds later for demo)
                val reminderRequest = OneTimeWorkRequestBuilder<TripReminderWorker>()
                    .setInitialDelay(10, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(requireContext()).enqueue(reminderRequest)

                // Schedule Update Simulation (20 seconds later for demo)
                val updateRequest = OneTimeWorkRequestBuilder<TripUpdateWorker>()
                    .setInitialDelay(20, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(requireContext()).enqueue(updateRequest)

                findNavController().popBackStack()
            }.onFailure {
                Toast.makeText(context, "Reservation Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.projet_couvoiturage.ui.employee.reservations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.data.local.entity.ReservationEntity
import com.example.projet_couvoiturage.databinding.FragmentReservationsBinding
import com.example.projet_couvoiturage.databinding.ItemReservationBinding
import com.example.projet_couvoiturage.domain.usecase.GetReservationsForUserUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ReservationsFragment : Fragment() {

    private var _binding: FragmentReservationsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReservationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val prefs = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("USER_ID", -1)
        
        val adapter = ReservationAdapter()
        binding.rvReservations.layoutManager = LinearLayoutManager(context)
        binding.rvReservations.adapter = adapter
        
        if (userId != -1L) {
            viewModel.getReservations(userId).observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val getReservationsForUserUseCase: GetReservationsForUserUseCase
) : ViewModel() {
    fun getReservations(userId: Long) = getReservationsForUserUseCase(userId).asLiveData()
}

class ReservationAdapter : androidx.recyclerview.widget.ListAdapter<ReservationEntity, ReservationAdapter.ResViewHolder>(
    object : androidx.recyclerview.widget.DiffUtil.ItemCallback<ReservationEntity>() {
        override fun areItemsTheSame(oldItem: ReservationEntity, newItem: ReservationEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ReservationEntity, newItem: ReservationEntity) = oldItem == newItem
    }
) {
    class ResViewHolder(val binding: ItemReservationBinding) : RecyclerView.ViewHolder(binding.root)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResViewHolder {
        return ResViewHolder(ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: ResViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.tvResStatus.text = item.status
        holder.binding.tvResTripId.text = "Trip ID: ${item.tripId}"
    }
}

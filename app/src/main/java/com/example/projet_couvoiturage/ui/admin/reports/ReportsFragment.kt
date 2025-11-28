package com.example.projet_couvoiturage.ui.admin.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projet_couvoiturage.databinding.FragmentReportsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

@AndroidEntryPoint
class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReportsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.loadReports()
        
        viewModel.reportData.observe(viewLifecycleOwner) { data ->
            // Pie Chart - Occupancy
            val pieEntries = listOf(
                PieEntry(data.occupancyRate, "Occupied"),
                PieEntry(100f - data.occupancyRate, "Available")
            )
            val pieDataSet = PieDataSet(pieEntries, "Occupancy Rate")
            pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            pieDataSet.valueTextSize = 14f
            
            val pieData = PieData(pieDataSet)
            binding.pieChart.data = pieData
            binding.pieChart.description.isEnabled = false
            binding.pieChart.centerText = "Occupancy\n%.1f%%".format(data.occupancyRate)
            binding.pieChart.animateY(1000)
            binding.pieChart.invalidate()

            // Bar Chart - Trips per Route
            val barEntries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()
            var index = 0f
            
            data.tripsByRoute.forEach { (route, count) ->
                barEntries.add(BarEntry(index, count.toFloat()))
                labels.add(route)
                index++
            }
            
            val barDataSet = BarDataSet(barEntries, "Trips per Route")
            barDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
            barDataSet.valueTextSize = 12f
            
            val barData = BarData(barDataSet)
            binding.barChart.data = barData
            binding.barChart.description.isEnabled = false
            binding.barChart.animateY(1000)
            binding.barChart.invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

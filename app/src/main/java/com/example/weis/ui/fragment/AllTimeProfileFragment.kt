package com.example.weis.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weis.R
import com.example.weis.databinding.FragmentAllTimeProfileBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class AllTimeProfileFragment : Fragment() {

    private lateinit var binding : FragmentAllTimeProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllTimeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.graphAnalytics
    }

    private fun setUpChart(){

        val entries = listOf(Entry(1f, 2f), Entry(2f, 5f), Entry(3f, 8f), Entry(4f, 3f))

        // Create a dataset
        val dataSet = LineDataSet(entries, "Label") // "Label" is the dataset label

        // Customize the dataset appearance
        dataSet.color = R.color.primaryColor
        dataSet.valueTextColor = R.color.textColor

        // Create a LineData object and add the dataset
        val lineData = LineData(dataSet)

        // Set data to the chart
        binding.graphAnalytics.data = lineData

        // Customize other chart properties if needed
        binding.graphAnalytics.description.isEnabled = false
        binding.graphAnalytics.setTouchEnabled(true)
        binding.graphAnalytics.isDragEnabled = true
        binding.graphAnalytics.setScaleEnabled(true)
    }
}
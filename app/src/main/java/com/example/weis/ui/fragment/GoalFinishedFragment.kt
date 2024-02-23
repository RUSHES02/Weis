package com.example.weis.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weis.R
import com.example.weis.databinding.FragmentGoalFinishedBinding


class GoalFinishedFragment : Fragment() {

    private lateinit var binding : FragmentGoalFinishedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGoalFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

}
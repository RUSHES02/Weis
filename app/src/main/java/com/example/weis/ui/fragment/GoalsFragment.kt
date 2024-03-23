package com.example.weis.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weis.adapter.GoalsRecyclerAdapter
import com.example.weis.databinding.FragmentGoalsBinding
import com.example.weis.modals.Goal


class GoalsFragment : Fragment() {

    private lateinit var binding : FragmentGoalsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGoalsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var id = 0
        val goals =  ArrayList<Goal>()
        val goalsAdapter = GoalsRecyclerAdapter()
        val goal = Goal(id = id, goal = "Aaj tera machaiga tera bhai", duration = "55 m")
        id++
        goals.add(goal)
        goalsAdapter.saveData(goals)
        binding.recyclerGoals.layoutManager = LinearLayoutManager(context)
        binding.recyclerGoals.adapter = goalsAdapter

        binding.flotBtnAddGoal.setOnClickListener{
            val goal = Goal(id = id, goal = "Aaj tera machaiga tera bhai", duration = "55 m")
            id++
            goals.add(goal)
            goalsAdapter.saveData(goals)
            binding.recyclerGoals.scrollToPosition(goals.size - 1)
        }
    }
}
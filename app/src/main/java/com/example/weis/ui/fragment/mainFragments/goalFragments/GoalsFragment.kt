package com.example.weis.ui.fragment.mainFragments.goalFragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weis.adapter.GoalsRecyclerAdapter
import com.example.weis.databinding.FragmentGoalsBinding
import com.example.weis.modals.Goal
import com.example.weis.modals.User
import com.example.weis.ui.activity.MainContainerActivity
import com.example.weis.utils.DialogClickListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


class GoalsFragment : Fragment(), DialogClickListener {

    private lateinit var binding : FragmentGoalsBinding
    private val goals =  ArrayList<Goal>()
    private val goalsAdapter = GoalsRecyclerAdapter()
    private lateinit var goalCollectionReference :CollectionReference
//    private val mainContainerActivity : MainContainerActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGoalsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("User", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString("user", null)
        val user = userDataJson?.let { Gson().fromJson(it, User::class.java)}

        goalsAdapter.saveData(goals)
        binding.recyclerGoals.layoutManager = LinearLayoutManager(context)
        binding.recyclerGoals.adapter = goalsAdapter
        goalCollectionReference = FirebaseFirestore.getInstance().collection("User").document(user!!.email).collection("Goals")

        goalsAdapter.onStartClick = { goal ->
            Log.d("Start Button","goal page")
            (requireActivity() as MainContainerActivity).changeTab(0, goal)
        }
        goalCollectionReference.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){
                    val data = document.data
                    val goal = Goal(
                        id = document.id,
                        goal = data["name"].toString(),
                        duration = data["duration"].toString(),
                        date = data["date"].toString(),
                        time = data["time"].toString()
                    )
                    goals.add(goal)
                    goalsAdapter.saveData(goals)
                    binding.recyclerGoals.scrollToPosition(goals.size - 1)
                }
            }.addOnFailureListener{
                Log.e("Goals fetch", "Failure")
            }
        binding.flotBtnAddGoal.setOnClickListener{
            val goalDialog = GoalSetDialogFragment()
            goalDialog.setListener(this)
            goalDialog.show((activity as AppCompatActivity).supportFragmentManager, "show Goal")
        }
    }

    override fun onDataPassed(data: Goal) {

        val goal = mapOf(
            "name" to data.goal,
            "duration" to data.duration,
            "date" to data.date,
            "time" to data.time
        )
        goalCollectionReference.add(goal)
            .addOnSuccessListener { documentReference ->
                data.id = documentReference.id
            }.addOnFailureListener { e ->
                Log.e("Goal upload", "failure", e)
            }
        goals.add(data)
        goalsAdapter.saveData(goals)
        binding.recyclerGoals.scrollToPosition(goals.size - 1)
    }
}
package com.example.weis.ui.fragment.mainFragments.focusFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weis.R
import com.example.weis.databinding.FragmentFocusBinding
import com.example.weis.modals.Goal
import com.example.weis.ui.fragment.mainFragments.focusFragments.sub.PlayFragment
import com.example.weis.ui.fragment.mainFragments.focusFragments.sub.StartMusicFragment

class FocusFragment: Fragment() {

    private lateinit var binding : FragmentFocusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFocusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    fun receiveData(goal : Goal?){
        val newFragment = StartMusicFragment(goal)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fragContFocus, newFragment)
            .addToBackStack(null)
            .commit()
    }

    interface DataChangeListener {
        fun sendData(data: Goal?)
    }

}

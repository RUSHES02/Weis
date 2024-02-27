package com.example.weis.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weis.R
import com.example.weis.databinding.FragmentStartMusicBinding

class StartMusicFragment : Fragment() {


    private lateinit var binding : FragmentStartMusicBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imgBtnPlay.setOnClickListener{
            Log.d("start music", "sensed clicked")
            val newFragment = PlayFragment()
            val fragmentManager = parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragContFocus, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
//
//    class ParentFragment : Fragment(), FocusFragment.OnNestedFragmentInteractionListener {
//        override fun onInteraction(data: String) {
//            // Handle the interaction
//        }
//    }

}
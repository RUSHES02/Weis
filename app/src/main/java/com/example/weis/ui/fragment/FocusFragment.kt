package com.example.weis.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weis.R
import com.example.weis.databinding.FragmentFocusBinding

class FocusFragment : Fragment() {

    private lateinit var binding : FragmentFocusBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFocusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val childFragmentManager = childFragmentManager
//
//        val nestedFragment = StartMusicFragment()
//        childFragmentManager.beginTransaction()
//            .replace(R.id.fragContFocus, nestedFragment)
//            .commit()
    }

//    interface OnNestedFragmentInteractionListener {
//        fun onInteraction(data: String)
//    }
//

}
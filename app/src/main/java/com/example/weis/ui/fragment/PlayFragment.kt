package com.example.weis.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weis.R
import com.example.weis.databinding.FragmentPlayBinding

class PlayFragment : Fragment() {


    private lateinit var binder : FragmentPlayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binder = FragmentPlayBinding.inflate(inflater, container, false)
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binder
    }

//    class ParentFragment : Fragment(), FocusFragment.OnNestedFragmentInteractionListener {
//        override fun onInteraction(data: String) {
//            // Handle the interaction
//        }
//    }

}
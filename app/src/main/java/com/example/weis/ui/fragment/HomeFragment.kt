package com.example.weis.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weis.R
import com.example.weis.databinding.FragmentHomeBinding
import com.example.weis.modals.User
import com.example.weis.ui.activity.ProfileActivity
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    lateinit var user : User
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DrawableCompat.setTint(binding.cardCenterIC.drawable, ContextCompat.getColor(requireContext(), R.color.primaryColor))

        sharedPreferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString("user", null)
        user = userDataJson?.let { Gson().fromJson(it, User::class.java)}!!

        Log.d("Saved User", "$user")

        Glide.with(this)
            .load((user as User).picture)
            .placeholder(R.drawable.img_user) // Add a placeholder drawable
            .error(R.drawable.img_user)// Add a drawable for error cases
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgUserProfile)

        if ((user as User).picture != null){
            Log.d("Saved User", "$user")

        }

        binding.imgUserProfile.setOnClickListener{
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))

        }

    }

    override fun onResume() {
        super.onResume()

        val userDataJson = sharedPreferences.getString("user", null)
        user = userDataJson?.let { Gson().fromJson(it, User::class.java)}!!


        Glide.with(this)
            .load(user.picture)
            .placeholder(R.drawable.img_user) // Add a placeholder drawable
            .error(R.drawable.img_user) // Add a drawable for error cases
            .into(binding.imgUserProfile)
    }
}
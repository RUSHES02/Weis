package com.example.weis.ui.fragment.mainFragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weis.R
import com.example.weis.databinding.FragmentHomeBinding
import com.example.weis.modals.User
import com.example.weis.ui.activity.ProfileActivity
import com.example.weis.viewModel.SharedViewModel
import com.google.gson.Gson
import java.text.DecimalFormat

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var user : User
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        sharedPreferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting the image for the center card
        DrawableCompat.setTint(binding.cardCenterIC.drawable, ContextCompat.getColor(requireContext(), R.color.primaryColor))

        setUiComponents()
        //setting the user image
        Glide.with(this)
            .load(user.picture)
            .placeholder(R.drawable.img_user) // Add a placeholder drawable
            .error(R.drawable.img_user)// Add a drawable for error cases
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgUserProfile)

        if (user.picture != null){
            Log.d("Saved User", "$user")

        }

        binding.imgUserProfile.setOnClickListener{
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))

        }

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        sharedViewModel.newUser.observe(viewLifecycleOwner) { newUser ->
            // Update UI with the new object

            binding.txtUsername.text = getString(R.string.greet, newUser.name?.split(" ")?.get(0))
            binding.txtTaskDoneNo.text = (newUser.tasksDone ?: 0).toString()

            if(newUser.secOfFocus!! < 60){
                binding.txtHrsFocNo.text = newUser.secOfFocus.toString()
                binding.txtHrsFoc.text = getString(R.string.time_focused, "sec")
            }else if(newUser.secOfFocus!! in 61..3600){
                Log.d("time in min", String.format("%.1f", (newUser.secOfFocus!! / 60.0F)))
                binding.txtHrsFocNo.text = (newUser.secOfFocus!! / 60).toString()
                binding.txtHrsFoc.text = getString(R.string.time_focused, "mins")
            }else{
                binding.txtHrsFocNo.text = (newUser.secOfFocus!! / 3600).toString()
                binding.txtHrsFoc.text = getString(R.string.time_focused, "hrs")
            }
        }

    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString("user", null)
        user = userDataJson?.let { Gson().fromJson(it, User::class.java)}!!


        Glide.with(this)
            .load(user.picture)
            .placeholder(R.drawable.img_user) // Add a placeholder drawable
            .error(R.drawable.img_user) // Add a drawable for error cases
            .into(binding.imgUserProfile)

    }

    private fun setUiComponents(context : Context = requireActivity()){
        //fetching the saved user from shared preference

        val sharedPref = context.getSharedPreferences("User", Context.MODE_PRIVATE)

        val userDataJson = sharedPref.getString("user", null)
        user = userDataJson?.let { Gson().fromJson(it, User::class.java)}!!
        Log.d("user", user.toString())

        binding.txtUsername.text = getString(R.string.greet, user.name?.split(" ")?.get(0))
        binding.txtTaskDoneNo.text = (user.tasksDone ?: 0).toString()

        val df = DecimalFormat("#.#")
        if(user.secOfFocus!! < 60){
            binding.txtHrsFocNo.text = user.secOfFocus.toString()
            binding.txtHrsFoc.text = getString(R.string.time_focused, "sec")
        }else if(user.secOfFocus!! in 61..3600){
            binding.txtHrsFocNo.text = (user.secOfFocus!! / 60).toString()
            binding.txtHrsFoc.text = getString(R.string.time_focused, "mins")
        }else{
            binding.txtHrsFocNo.text = (user.secOfFocus!!/ 3600).toString()
            binding.txtHrsFoc.text = getString(R.string.time_focused, "hrs")
        }
    }
}
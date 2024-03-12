package com.example.weis.ui.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weis.R
import com.example.weis.databinding.ActivityProfileBinding
import com.example.weis.modals.User
import com.example.weis.utils.CheckNetwork
import com.example.weis.utils.StoreUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var storageRef : StorageReference
    private lateinit var dbRef : CollectionReference
    private var uri : Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString("user", null)
        val user = userDataJson?.let { Gson().fromJson(it, User::class.java)}

        binding.editTextEditName.visibility = View.GONE

        if (user != null) {
            if (user.picture != null){
                Log.d("Saved User", "$user")
                Glide.with(this)
                    .load(user.picture)
                    .placeholder(R.drawable.img_user)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)// Add a placeholder drawable
                    .error(R.drawable.img_user) // Add a drawable for error cases
                    .into(binding.imgUser)
            }
            binding.textUsername.text = user.name
        }




        storageRef = FirebaseStorage.getInstance().getReference("Images")
        dbRef = FirebaseFirestore.getInstance().collection("User")


        binding.icEditName.setOnClickListener{
            if(CheckNetwork.isInternetAvailable(this)) {
                binding.editTextEditName.visibility = View.VISIBLE
            }else{
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            }
        }

        binding.constraintProfile.setOnClickListener{
            if(binding.editTextEditName.visibility == View.VISIBLE){
                binding.textUsername.text = binding.editTextEditName.text
                binding.editTextEditName.visibility = View.GONE
                if (user != null) {
                    user.name = binding.editTextEditName.text.toString()
                    StoreUser.saveData(user, this)
                    dbRef.document(user.email).update(mapOf("name" to user.name))
                        .addOnCompleteListener {
                            Log.d("dbref", "success")
                        }
                        .addOnFailureListener {
                            Log.d("dbref", "failed")
                        }
                }
            }
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.imgUser.setImageURI(uri)
            Log.d("picker", "success")

            if (uri != null) {
                storageRef.child(user!!.email).putFile(uri)
                    .addOnSuccessListener { task ->
                        Log.d("storage", "success")
                        Log.d("email", "$user")

                        // Retrieve the download URL of the uploaded image
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                val img = url.toString()
                                user.picture = img
                                StoreUser.saveData(user, this)
                                // Update the user's document in Firestore with the image URL
                                dbRef.document(user.email).update(mapOf("picture" to img))
                                    .addOnCompleteListener {
                                        Log.d("dbref", "success")
                                    }
                                    .addOnFailureListener {
                                        Log.d("dbref", "failed")
                                    }
                            }
                    }
                    .addOnFailureListener {
                        Log.e("storage", "failed")
                    }
            }
        }


        binding.imgUser.setOnClickListener{
            if(CheckNetwork.isInternetAvailable(this)){
                pickImage.launch("image/*")
            }else{
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            }

        }

        binding.imgBtnBack.setOnClickListener{
            finish()
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        val resultIntent = Intent()
        return super.getOnBackInvokedDispatcher()
    }

}
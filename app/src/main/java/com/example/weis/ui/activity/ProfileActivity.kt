package com.example.weis.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.weis.databinding.ActivityProfileBinding
import com.example.weis.modals.User
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

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        val userDataJson = sharedPreferences.getString("user", null)
        val user = userDataJson?.let { Gson().fromJson(it, User::class.java)}

        storageRef = FirebaseStorage.getInstance().getReference("Images")
        dbRef = FirebaseFirestore.getInstance().collection("User")


        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.imgUser.setImageURI(uri)
            Log.d("picker", "success")
//            if (uri != null && !user?.email.isNullOrBlank()) {

            user?.email
            if (uri != null) {
                storageRef.child(user!!.email).putFile(uri)
                    .addOnSuccessListener { task ->
                        Log.d("storage", "success")
                        Log.d("email", "$user")

                        // Retrieve the download URL of the uploaded image
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { url ->
                                val img = url.toString()

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
//            }
        }


        binding.imgUser.setOnClickListener{

            pickImage.launch("image/*")

        }
    }

}
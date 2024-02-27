package com.example.weis.ui.fragment

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weis.R
import com.example.weis.databinding.FragmentRegistrationBinding
import com.example.weis.modals.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

class RegistrationFragment : Fragment() {

    private lateinit var  binding : FragmentRegistrationBinding
    private lateinit var  firebaseAuth : FirebaseAuth
    private lateinit var deRef : CollectionReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        deRef = FirebaseFirestore.getInstance().collection("User")

        binding.btnGoogleSignUp.text = getString(R.string.google_btn_text, getString(R.string.sign_up), getString(R.string.with_google))
        var user : User

        binding.btnSignUp.setOnClickListener{
            user = User(
                email = binding.editTextEmail.text.toString(),
                name = binding.editTextName.text.toString(),
                password = binding.editTextPass.text.toString()
            )
            if(validateField(user)) registerUser(user)
        }
    }

    private fun validateField(user: User): Boolean {
        if(user.name.isNotEmpty() && user.email.isNotEmpty() && user.password.isNotEmpty()) {
            user.name.forEach{char ->
                if (char.isDigit()){
                    Log.d("name", " has digit $char")
                    binding.editTextName.error = "Invalid Username"
                    return false
                }
            }

            if(!Pattern.matches(Patterns.EMAIL_ADDRESS.pattern(),user.email)){
                binding.editTextEmail.error = "Invalid Email Address"
                return false
            }

        }
        else{
            Toast.makeText(context, "Empty Fields are not allowed" , Toast.LENGTH_SHORT).show()
            return false

        }

        return true
    }

    private fun registerUser(user: User){
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show()
                binding.editTextName.setText("")
                binding.editTextEmail.setText("")
                binding.editTextPass.setText("")
            }.addOnFailureListener {
                Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
            }

        deRef.document(user.email).set(mapOf("name" to user.name))
            .addOnSuccessListener {
                Toast.makeText(context, "Firestore Successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context, "firestore Failed", Toast.LENGTH_SHORT).show()
            }
    }
}
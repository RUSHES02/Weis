package com.example.weis.ui.fragment

//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.common.api.GoogleApiClient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weis.R
import com.example.weis.databinding.FragmentRegistrationBinding
import com.example.weis.modals.User
import com.example.weis.ui.activity.MainContainerActivity
import com.example.weis.utils.StoreUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

class RegistrationFragment : Fragment() {

    private lateinit var  binding : FragmentRegistrationBinding
    private lateinit var deRef : CollectionReference

    private lateinit var  firebaseAuth : FirebaseAuth

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

//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .requestIdToken(getString(R.string.web_client_id))
//            .requestProfile()
//            .build()

//        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//        mGoogleSignInClient = Goo.

        binding.btnGoogleSignUp.text = getString(R.string.concat, getString(R.string.sign_up), getString(R.string.with_google))
        var user : User

        binding.btnSignUp.setOnClickListener{
            user = User(
                email = binding.editTextEmail.text.toString(),
                name = binding.editTextName.text.toString(),
                password = binding.editTextPass.text.toString(),
                picture = null
            )
            if(validateField(user)) registerUser(user)
        }

        binding.btnGoogleSignUp.setOnClickListener{
//            signIn()
        }
    }

    private fun validateField(user: User): Boolean {
        if(user.name?.isNotEmpty() == true && user.email.isNotEmpty() && user.password.isNotEmpty()) {
            user.name.forEach{ char ->
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

                deRef.document(user.email).set(mapOf("name" to user.name))
                    .addOnSuccessListener {
                        Toast.makeText(context, "Firestore Successfully", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(context, "firestore Failed", Toast.LENGTH_SHORT).show()
                    }
                StoreUser.saveData(user, requireActivity())
                val intent = Intent(requireActivity(), MainContainerActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun signIn() {
//        val signInIntent = mGoogleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//    }
//
//    private fun handleSignInResult(completedTask: Task<com.google.android.gms.auth.api.signin.GoogleSignInAccount>) {
//        try {
//            val account = completedTask.getResult(ApiException::class.java)
//            // Signed in successfully, handle account data (e.g., send it to your server)
//            updateUI(account)
//        } catch (e: ApiException) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w("google", "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
//        }
//    }
//
//    private fun updateUI(account: com.google.android.gms.auth.api.signin.GoogleSignInAccount?) {
//        // Handle the UI update after sign-in (e.g., update UI elements, navigate to the next screen)
//        if (account != null) {
//            // Successful sign-in, you can use account information
//            val displayName = account.displayName
//            val email = account.email
//            // ...
//
//            Log.d("creds", "$displayName, $email")
//        } else {
//            // Sign-in failed
//        }
//    }
}
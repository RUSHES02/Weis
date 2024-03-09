package com.example.weis.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.weis.R
import com.example.weis.modals.User
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class  GoogleAuth(
    private val context: Context,
    private val oneTapClient : SignInClient
) {

    private val auth = Firebase.auth


    suspend fun signIn() : IntentSender?{
        val result = try{
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()

        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent) : SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            SignInResult(
                data = user?.run{
                    User(
                        id = uid,
                        name = displayName.toString(),
                        picture = photoUrl?.toString(),
                        email = "",
                        password = ""
                    )
                },
                errorMsg = null
            )
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
            SignInResult(data = null, errorMsg = e.message)
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
    }

    fun getSignedInUser() : User? = auth.currentUser?.run {
        User(
            id = uid,
            name = displayName.toString(),
            picture = photoUrl?.toString(),
            email = "",
            password = ""
        )
    }
    private fun buildSignInRequest(): BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}
package com.example.newsapp.features.auth

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.newsapp.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class AuthViewModal @Inject constructor(
) : ViewModel() {

    private var auth: FirebaseAuth

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName

    init {
        auth = Firebase.auth
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateFullName(fullName: String) {
        _fullName.value = fullName
    }

    fun createUser(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(
            email.value,
            password.value
        )
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure()
                }
            })

    }

    fun signInUser(
        onSuccess: (FirebaseUser?) -> Unit,
        onFailure: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onSuccess(user)
                } else {
                    onFailure()
                }
            }
    }

    fun signInWithGoogle(
        context: Context,
        onExecute: (Intent) -> Unit
    ) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("205577658561-goqbn8sgvr4l2cood71uak50hsoi64s7.apps.googleusercontent.com") // Replace with your Web Client ID
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent
        onExecute(signInIntent)
    }

    fun handleSignInResult(
        account: GoogleSignInAccount?,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        account?.let {
            val credential = GoogleAuthProvider.getCredential(it.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure()
                }
            }
        }
    }

    fun signOut(context: Context) {

        FirebaseAuth.getInstance().signOut()

        val loginIntent = Intent(context, AuthActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(loginIntent)
    }
}
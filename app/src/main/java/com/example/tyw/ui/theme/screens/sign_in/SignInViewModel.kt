package com.example.tyw.ui.theme.screens.sign_in

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import android.util.Log
import com.example.tyw.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel(), SignInActions {

    private val _signInUIState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState.Loading)
    val signInUIState = _signInUIState.asStateFlow()
    private var data = SignInData()

    override fun signInUser() {
        val email = data.email
        val password = data.password
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("SignInViewModel", "signInWithEmail:success")
                        _signInUIState.update { SignInState.UserSignedIn }
                    } else {
                        Log.e("SignInViewModel", "signInWithEmail:failure", task.exception)
                        _signInUIState.update { SignInState.SignInError(task.exception?.message ?: "Sign in failed") }
                    }
                }
        } else {
            _signInUIState.update { SignInState.UserChanged(data) }
            if (email.isEmpty()) {
                data.emailError = R.string.Error_pole
            }
            if (password.isEmpty()) {
                data.passwordError = R.string.Error_pole
            }
        }
    }

    override fun onEmailChanged(email: String) {
        data.email = email
        _signInUIState.update { SignInState.UserChanged(data) }
    }

    override fun onPasswordChanged(password: String) {
        data.password = password
        _signInUIState.update { SignInState.UserChanged(data) }
    }
}



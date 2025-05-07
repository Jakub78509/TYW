package com.example.tyw.ui.theme.screens.sign_up

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import android.util.Log
import com.example.tyw.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    ) : ViewModel(), SignUpActions {

    private val _signUpUIState: MutableStateFlow<SignUpState> = MutableStateFlow(value = SignUpState.laoding)
    val signUpUIState = _signUpUIState.asStateFlow()
    private var data = SignUpData()

    override fun saveUser() {
        val email = data.user.email
        val username = data.user.username
        val password = data.user.password
        val passwordConfirm = data.user.passwordConfirm

        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
            if (password == passwordConfirm) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName = username
                            }
                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { profileUpdateTask ->
                                    if (profileUpdateTask.isSuccessful) {
                                        Log.d("SignUpAuthViewModel", "User profile updated.")
                                    } else {
                                        Log.e(
                                            "SignUpAuthViewModel",
                                            "Failed to update user profile.",
                                            profileUpdateTask.exception
                                        )
                                    }

                                    _signUpUIState.update {
                                        SignUpState.UserSaved
                                    }
                                }
                        } else {
                            Log.e(
                                "SignUpAuthViewModel",
                                "createUserWithEmailAndPassword:failure",
                                task.exception
                            )

                            _signUpUIState.update {
                                SignUpState.UserChanged(data)
                            }
                        }
                    }
            } else {
                data.passwordError = R.string.Error_pole
                _signUpUIState.update {
                    SignUpState.UserChanged(data)
                }
            }
        } else {
            _signUpUIState.update {
                SignUpState.UserChanged(data)
            }

            if (email.isEmpty()) {
                data.emailError = R.string.Error_email
            }
            if (username.isEmpty()) {
                data.usernameError = R.string.Error_pole
            }
            if (password.isEmpty()) {
                data.passwordError = R.string.Error_heslo
            }
            if (passwordConfirm.isEmpty()) {
                data.passwordConfirmError = R.string.Error_heslo
            }
        }
    }



    override fun onUsernameChanged(username: String) {
        data.user.username = username
        _signUpUIState.update {
            SignUpState.UserChanged(data)
        }
    }
    override fun onPasswordChanged(password: String) {
        data.user.password = password
        _signUpUIState.update {
            SignUpState.UserChanged(data)
        }
    }
    override fun onEmailChanged(email: String) {
        data.user.email = email
        _signUpUIState.update {
            SignUpState.UserChanged(data)
        }
    }

    override fun onPasswordConfirmChanged(passwordConfirm: String) {
        data.user.passwordConfirm = passwordConfirm
        _signUpUIState.update {
            SignUpState.UserChanged(data)
        }
    }


}
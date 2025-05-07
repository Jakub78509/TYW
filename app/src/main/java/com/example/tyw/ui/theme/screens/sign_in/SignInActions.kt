package com.example.tyw.ui.theme.screens.sign_in



interface SignInActions {
    fun signInUser()
    fun onEmailChanged(email: String)
    fun onPasswordChanged(password: String)
}

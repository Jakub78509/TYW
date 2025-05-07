package com.example.tyw.ui.theme.screens.sign_up

interface SignUpActions {
    fun onEmailChanged(email: String)
    fun onPasswordChanged(password: String)
    fun onUsernameChanged(username: String)
    fun saveUser()
    fun onPasswordConfirmChanged(passwordConfirm: String)

}
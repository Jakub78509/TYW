package com.example.tyw.ui.theme.screens.sign_in

data class SignInData(
    var email: String = "",
    var password: String = "",
    var emailError: Int? = null,
    var passwordError: Int? = null
)
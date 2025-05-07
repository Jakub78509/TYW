package com.example.tyw.ui.theme.screens.sign_up

import com.example.tyw.model.SignUpUsers

class SignUpData {
    var user: SignUpUsers = SignUpUsers("", "", "","")
    var emailError: Int? = null
    var passwordError: Int? = null
    var usernameError: Int? = null
    var passwordConfirmError: Int? = null

}
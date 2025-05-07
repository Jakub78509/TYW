package com.example.tyw.ui.theme.screens.sign_up


sealed class SignUpState {
    object laoding : SignUpState()
    object UserSaved : SignUpState()
    class UserChanged(val data: SignUpData) : SignUpState()
}
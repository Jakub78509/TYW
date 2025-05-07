package com.example.tyw.ui.theme.screens.sign_in

sealed class SignInState {
    object Loading : SignInState()
    object UserSignedIn : SignInState()
    class UserChanged(val data: SignInData) : SignInState()
    class SignInError(val error: String) : SignInState()
}
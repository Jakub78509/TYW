package com.example.tyw.model

data class SignUpUsers(var email: String,
                       var password: String,
                       var passwordConfirm: String,
                       var username: String)
data class UserLogin(
    var email: String,
    var password: String,)



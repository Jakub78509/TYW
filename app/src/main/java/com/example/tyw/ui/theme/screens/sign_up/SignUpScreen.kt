package com.example.tyw.ui.theme.screens.sign_up

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tyw.navigations.INavigationsRouter
import com.example.tyw.ui.theme.BasicMargin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navigationRouter: INavigationsRouter) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val state = viewModel.signUpUIState.collectAsState()
    var data by remember { mutableStateOf(SignUpData()) }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }

    state.value.let {
        when (it) {
            is SignUpState.UserChanged -> {
                data = it.data
            }
            is SignUpState.laoding -> {

            }
            SignUpState.UserSaved -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Register")
        }, navigationIcon = {
            IconButton(onClick = { navigationRouter.returnBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "arrow_back",
                    tint = Color(0xFF6200EE)
                )
            }
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(BasicMargin()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = data.user.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                isError = data.emailError != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            if (data.emailError != null) {
                Text(text = "Is your email right? -> pepa@sezna.cz")
            }

            Spacer(modifier = Modifier.height(BasicMargin()))

            TextField(
                value = data.user.username,
                onValueChange = { viewModel.onUsernameChanged(it) },
                label = { Text("Username") },
                isError = data.usernameError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (data.usernameError != null) {
                Text(text = "You must write something here")
            }

            Spacer(modifier = Modifier.height(BasicMargin()))

            TextField(
                value = data.user.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Password") },
                isError = data.passwordError != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (data.passwordError != null) {
                Text(text = "Password needs to be 6 characters or more")
            }

            Spacer(modifier = Modifier.height(BasicMargin()))

            TextField(
                value = data.user.passwordConfirm,
                onValueChange = { viewModel.onPasswordConfirmChanged(it) },
                label = { Text("Confirm Password") },
                isError = data.passwordConfirmError != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordConfirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordConfirmVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        passwordConfirmVisible = !passwordConfirmVisible
                    }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (data.passwordConfirmError != null) {
                Text(text = "Passwords do not match")
            }

            Spacer(modifier = Modifier.height(BasicMargin()))

            Button(
                onClick = { viewModel.saveUser() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}

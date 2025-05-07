package com.example.tyw.ui.theme.screens.sign_in

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tyw.R
import com.example.tyw.navigations.INavigationsRouter
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.HalfMargin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navigationRouter: INavigationsRouter) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val state = viewModel.signInUIState.collectAsState()
    var data by remember { mutableStateOf(SignInData()) }
    var passwordVisible by remember { mutableStateOf(false) }

    state.value.let {
        when (it) {
            is SignInState.UserChanged -> {
                data = it.data
            }
            is SignInState.Loading -> {

            }
            is SignInState.SignInError -> {
                Text(text = "Problém")
            }
            is SignInState.UserSignedIn -> {
                LaunchedEffect(it) {
                    navigationRouter.navigateToHomePageScreen()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(text = stringResource(id = R.string.log_in), modifier = Modifier.align(Alignment.Center))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(BasicMargin()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = data.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                isError = data.emailError != null,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            if (data.emailError != null) {
                Text(text = stringResource(id = R.string.wrong_email))
            }

            Spacer(modifier = Modifier.height(HalfMargin()))

            TextField(
                value = data.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Password") },
                isError = data.passwordError != null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            if (data.passwordError != null) {
                Text(text = stringResource(id = R.string.wrong_password))
            }

            Spacer(modifier = Modifier.height(BasicMargin()))

            Button(
                onClick = { viewModel.signInUser() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.sing_ip))
            }

            Spacer(modifier = Modifier.height(BasicMargin()))

            Button(
                onClick = {
                    navigationRouter.navigateToSingUpScreen()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.sing_up))
            }
        }
    }
}

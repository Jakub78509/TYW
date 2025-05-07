package com.example.tyw.ui.theme.screens.targetweight

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tyw.R
import com.example.tyw.navigations.INavigationsRouter
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.HalfMargin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetWeightPageScreen(
    navigationRouter: INavigationsRouter
) {
    val viewModel = hiltViewModel<TargetWeightViewModel>()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.Target)) },
                navigationIcon = {
                    IconButton(onClick = { navigationRouter.returnBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "arrow_back",
                            tint = Color(0xFF6200EE)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            TargetWeightContent(paddingValues = paddingValues, viewModel = viewModel, navigationRouter = navigationRouter, errorMessage = errorMessage)
        }
    )
}

@Composable
fun TargetWeightContent(paddingValues: PaddingValues, viewModel: TargetWeightViewModel, navigationRouter: INavigationsRouter, errorMessage: String?) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("0.0")) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            label = { Text(stringResource(id = R.string.add_target)) },
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(HalfMargin()))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(BasicMargin()))

        Button(
            onClick = {
                val weight = textFieldValue.text.toDoubleOrNull()
                if (weight != null && weight > 0) {
                    viewModel.saveTargetWeight(weight)
                    navigationRouter.returnBack()
                } else {
                    viewModel.saveTargetWeight(-1.0)
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(stringResource(id = R.string.send))
        }
    }
}

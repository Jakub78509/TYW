package com.example.tyw.ui.theme.screens.bmi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tyw.R
import com.example.tyw.navigations.INavigationsRouter
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.HalfMargin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiWeightPageScreen(navigationRouter: INavigationsRouter) {
    val viewModel = hiltViewModel<BmiViewModel>()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.BMI))
        }, navigationIcon = {
            IconButton(onClick = { navigationRouter.returnBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "arrow_back",
                    tint = Color(0xFF6200EE)
                )
            }
        })
    }) {
        BmiPageScreenContent(paddingValues = it, viewModel)
    }
}

@Composable
fun BmiPageScreenContent(paddingValues: PaddingValues, viewModel: BmiViewModel) {
    val weight by viewModel.weight
    val height by viewModel.height
    val result by viewModel.result
    val gender by viewModel.gender

    val maleText = stringResource(id = R.string.sex_male)
    val femaleText = stringResource(id = R.string.sex_female)
    val underweightText = stringResource(id = R.string.Underweight)
    val normalWeightTextMale = stringResource(id = R.string.normal_weight_male)
    val normalWeightTextFemale = stringResource(id = R.string.norma_weight_female)
    val overweightText = stringResource(id = R.string.Overweight)
    val obesityText = stringResource(id = R.string.Obesity)
    val errorText = stringResource(id = R.string.Error_pole)

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(BasicMargin()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = weight,
            onValueChange = { viewModel.weight.value = it },
            label = { Text(text = stringResource(id = R.string.weight_in)) },
            modifier = Modifier
                .padding(HalfMargin())
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = height,
            onValueChange = { viewModel.height.value = it },
            label = { Text(text = stringResource(id = R.string.height)) },
            modifier = Modifier
                .padding(HalfMargin())
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        var expanded by remember { mutableStateOf(false) }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(HalfMargin())) {
            OutlinedTextField(
                value = gender,
                onValueChange = { },
                label = { Text(text = stringResource(id = R.string.sex)) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text(text = maleText) },
                    onClick = {
                        viewModel.gender.value = maleText
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = femaleText) },
                    onClick = {
                        viewModel.gender.value = femaleText
                        expanded = false
                    }
                )
            }
        }

        Button(
            onClick = {
                viewModel.calculateBMI(
                    underweightText,
                    normalWeightTextMale,
                    normalWeightTextFemale,
                    overweightText,
                    obesityText,
                    errorText
                )
            },
            modifier = Modifier
                .padding(HalfMargin())
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.calculate_bmi))
        }

        val resultColor = when {
            result.contains(underweightText) -> Color.Blue
            result.contains(normalWeightTextMale) || result.contains(normalWeightTextFemale) -> Color.Green
            result.contains(overweightText) -> Color.Yellow
            result.contains(obesityText) -> Color.Red
            result.contains(errorText) -> Color.Red
            else -> Color.Black
        }

        Text(
            text = result,
            modifier = Modifier.padding(HalfMargin()),
            textAlign = TextAlign.Center,
            color = resultColor
        )
    }
}

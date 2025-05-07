package com.example.tyw.ui.theme.screens.bmi

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class BmiViewModel : ViewModel() {
    var weight = mutableStateOf("")
    var height = mutableStateOf("")
    var gender = mutableStateOf("Male")
    var result = mutableStateOf("")
    var resultColor = mutableStateOf(Color.Black)

    fun calculateBMI(
        underweightText: String,
        normalWeightTextMale: String,
        normalWeightTextFemale: String,
        overweightText: String,
        obesityText: String,
        Error: String
    ) {
        val weightValue = weight.value.toDoubleOrNull()
        val heightValue = height.value.toDoubleOrNull()

        if (weightValue != null && heightValue != null && heightValue > 0) {
            val heightM = heightValue / 100
            val bmi = weightValue / (heightM * heightM)

            result.value = when {
                bmi < 18.5 -> underweightText
                bmi < 24.9 -> if (gender.value == "Male") normalWeightTextMale else normalWeightTextFemale
                bmi < 29.9 -> overweightText
                else -> obesityText
            }
            resultColor.value = Color.Black
        } else {
            result.value = Error
            resultColor.value = Color.Red
        }
    }
}

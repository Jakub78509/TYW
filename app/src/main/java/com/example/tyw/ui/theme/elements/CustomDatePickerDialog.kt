package com.example.tyw.ui.theme.elements

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tyw.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CustomDatePickerDialog(
    modifier: Modifier = Modifier,
    date: Long? = null,
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
){

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)

    DatePickerDialog(onDismissRequest = { onDismiss() },
        confirmButton = {

            Button(onClick = { onDateSelected(datePickerState.selectedDateMillis!!) }) {
                Text(text = stringResource(id = R.string.Select))

            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.Cancel))

            }
        },
        modifier = modifier)
    {
        DatePicker(state = datePickerState)
    }

}
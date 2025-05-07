package com.example.tyw.ui.theme.screens.AddEdit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.unit.dp
import com.example.tyw.R
import com.example.tyw.navigations.INavigationsRouter
import com.example.tyw.ui.theme.BasicMargin
import com.example.tyw.ui.theme.elements.AddElement
import com.example.tyw.ui.theme.elements.CustomDatePickerDialog
import com.example.tyw.utils.DateUtilitis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditWeightPageScreen(navigationRouter: INavigationsRouter, id: Long?) {
    val viewModel = hiltViewModel<AddEditWeightScreenViewModel>()
    val state = viewModel.addEditWeightUIState.collectAsStateWithLifecycle()
    var data by remember {
        mutableStateOf(AddEditWeightPageScreenData())
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    state.value.let {
        when (it) {
            AddEditWeightUIState.Loading -> {
                viewModel.loadWeight(id)
            }
            is AddEditWeightUIState.WeightChanged -> {
                data = it.data
            }
            is AddEditWeightUIState.AddNewWeight -> {
                data = it.data
            }
            AddEditWeightUIState.RecordSaved -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }
            AddEditWeightUIState.WeightDeleted -> {
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(id = R.string.confirm_deletion)) },
            text = { Text(stringResource(id = R.string.are_you_sure_delete)) },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.deleteTask()
                }) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        color = Color.Red
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (id != null) stringResource(id = R.string.edit_weihgt) else stringResource(
                        id = R.string.add_weihgt
                    ))
                },
                actions = {
                    if (id != null) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    }
                },
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
        }
    ) {
        AddEditWeightScreenContent(
            paddingValues = it,
            data = data,
            actions = viewModel
        )
    }
}

@Composable
fun AddEditWeightScreenContent(
    paddingValues: PaddingValues,
    data: AddEditWeightPageScreenData,
    actions: AddEditWeightPageScreenActions
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(BasicMargin()))

        AddElement(
            icon = Icons.Default.DateRange,
            value = data.weight.date?.let { DateUtilitis.getDateString(it) },
            hint = stringResource(id = R.string.date),
            onClick = {
                showDatePickerDialog = true
            },
            onClearClick = {
                actions.onDateChanged(null)
            }
        )

        if (showDatePickerDialog) {
            CustomDatePickerDialog(
                onDateSelected = {
                    actions.onDateChanged(it)
                    showDatePickerDialog = false
                },
                onDismiss = {
                    showDatePickerDialog = false
                }
            )
        }

        Spacer(modifier = Modifier.height(BasicMargin()))

        OutlinedTextField(
            value = data.weight.vaha.toString(),
            onValueChange = { newValue ->
                val formattedValue = newValue.toDoubleOrNull()?.let { value ->
                    String.format("%.4f", value)
                } ?: ""
                val regex = """\d{0,4}(\.\d{0,4})?""".toRegex()
                if (regex.matches(formattedValue)) {
                    actions.onWeightChange(formattedValue.toDoubleOrNull() ?: 0.0)
                }
            },
            label = { Text(text = stringResource(id = R.string.weight)) },
            isError = data.weightError != null,
            supportingText = {
                if (data.weightError != null) {
                    Text(text = stringResource(id = data.weightError!!))
                } else {
                    Text(text = stringResource(id = R.string.weight_in))
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { actions.saveWeight() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = stringResource(id = R.string.send))
        }
    }
}

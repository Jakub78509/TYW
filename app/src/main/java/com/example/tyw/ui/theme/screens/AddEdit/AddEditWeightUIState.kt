package com.example.tyw.ui.theme.screens.AddEdit

sealed class AddEditWeightUIState {
    object Loading: AddEditWeightUIState()
    object RecordSaved: AddEditWeightUIState()
    class AddNewWeight(val data: AddEditWeightPageScreenData): AddEditWeightUIState()
    class WeightChanged(val data: AddEditWeightPageScreenData): AddEditWeightUIState()
    object WeightDeleted: AddEditWeightUIState()
}
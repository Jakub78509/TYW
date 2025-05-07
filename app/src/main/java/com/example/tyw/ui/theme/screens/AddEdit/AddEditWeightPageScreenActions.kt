package com.example.tyw.ui.theme.screens.AddEdit

interface AddEditWeightPageScreenActions {
    fun saveWeight()
    fun onWeightChange(vaha: Double)
    fun onDateChanged(date: Long?)
    fun deleteTask()
}
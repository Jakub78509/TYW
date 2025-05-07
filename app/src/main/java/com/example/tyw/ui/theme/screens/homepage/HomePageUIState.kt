package com.example.tyw.ui.theme.screens.homepage


import com.example.tyw.model.Weight

sealed class HomePageUIState {

    class Loading: HomePageUIState()
    class Success(val weight: List<Weight>):HomePageUIState()

}
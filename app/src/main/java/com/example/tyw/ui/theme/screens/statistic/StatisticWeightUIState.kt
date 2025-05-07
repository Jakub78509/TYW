package com.example.tyw.ui.theme.screens.statistic

import com.example.tyw.model.Weight
import com.example.tyw.ui.theme.screens.homepage.HomePageUIState

sealed class StatisticWeightUIState {
    object Loading : StatisticWeightUIState()
    data class Success(val weight: List<Weight>) : StatisticWeightUIState()
}

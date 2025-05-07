package com.example.tyw.ui.theme.screens.statistic


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tyw.databases.ILocalWeightRepository
import com.example.tyw.ui.theme.screens.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticWeightViewModel @Inject constructor(
    private val repository: ILocalWeightRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatisticWeightUIState>(StatisticWeightUIState.Loading)
    val uiState: StateFlow<StatisticWeightUIState> = _uiState

    init {
        loadWeightData()
    }

    private fun loadWeightData() {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            repository.getAll(userId)
                .map { data ->
                    val validData = data.filter { it.date != null }
                    StatisticWeightUIState.Success(validData)
                }
                .collect { uiState ->
                    _uiState.value = uiState
                }
        }
    }
}



package com.example.tyw.ui.theme.screens.targetweight

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tyw.datastore.IDataStoreRepository
import com.example.tyw.ui.theme.screens.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.tyw.R
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltViewModel
class TargetWeightViewModel @Inject constructor(
    private val dataStoreRepository: IDataStoreRepository,
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
): ViewModel(), TargetWeightPageScreenActions {

    private val _targetWeight = MutableStateFlow(0.0)
    val targetWeight: StateFlow<Double> = _targetWeight

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        getUserId()
    }

    private fun getUserId() {
        viewModelScope.launch {
            authRepository.getCurrentUserIdFlow().collect { userId ->
                if (!userId.isNullOrEmpty()) {
                    loadTargetWeight(userId)
                }
            }
        }
    }

    private fun loadTargetWeight(userId: String) {
        viewModelScope.launch {
            dataStoreRepository.getWeight(userId).collect { target ->
                _targetWeight.value = target
            }
        }
    }

    override fun saveTargetWeight(target: Double) {
        viewModelScope.launch {
            if (target <= 0) {
                _errorMessage.value = context.getString(R.string.Error_pole)
            } else {
                val userId = authRepository.getCurrentUserId()
                if (!userId.isNullOrEmpty()) {
                    dataStoreRepository.setWeight(userId, target)
                    _errorMessage.value = null
                    _targetWeight.value = target
                }
            }
        }
    }

    override fun deleteWeight() {
        viewModelScope.launch {
            _targetWeight.value = 0.0
            val userId = authRepository.getCurrentUserId()
            if (!userId.isNullOrEmpty()) {
                dataStoreRepository.setWeight(userId, 0.0)
            }
        }
    }
}

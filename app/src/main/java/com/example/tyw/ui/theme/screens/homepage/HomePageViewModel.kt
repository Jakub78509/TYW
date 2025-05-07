package com.example.tyw.ui.theme.screens.homepage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tyw.databases.ILocalWeightRepository
import com.example.tyw.ui.theme.screens.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: ILocalWeightRepository, private val authRepository: AuthRepository) : ViewModel(), HomePageScreenActions {

    val homePageUIState: MutableState<HomePageUIState> = mutableStateOf(HomePageUIState.Loading())

    fun loadRecord(s: String) {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            repository.getAll(userId).collect(){
                homePageUIState.value = HomePageUIState.Success(it)
            }
        }
    }

    override fun changeWeightState(id: Long, state: Boolean){
        viewModelScope.launch {
            repository.changeWeightState(id, state )
        }
    }
}

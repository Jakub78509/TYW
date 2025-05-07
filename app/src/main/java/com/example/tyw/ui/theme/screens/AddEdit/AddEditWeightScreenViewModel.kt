package com.example.tyw.ui.theme.screens.AddEdit


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tyw.R
import com.example.tyw.databases.ILocalWeightRepository
import com.example.tyw.ui.theme.screens.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditWeightScreenViewModel @Inject constructor(
    private val repository: ILocalWeightRepository,
    private val authRepository: AuthRepository
): ViewModel(), AddEditWeightPageScreenActions {

    private val _addEditWeightUIState: MutableStateFlow<AddEditWeightUIState> = MutableStateFlow(AddEditWeightUIState.Loading)
    val addEditWeightUIState = _addEditWeightUIState.asStateFlow()
    private var data = AddEditWeightPageScreenData()

    init {
        getUserId()
    }

    private fun getUserId() {
        viewModelScope.launch {
            authRepository.getCurrentUserIdFlow().collect { userId ->
                data.userId = userId
            }
        }
    }

    override fun onWeightChange(vaha: Double) {
        data.weight.vaha = vaha
        _addEditWeightUIState.update {
            AddEditWeightUIState.AddNewWeight(data)
        }
    }

    override fun onDateChanged(date: Long?) {
        data.weight.date =  date
        _addEditWeightUIState.update {
            AddEditWeightUIState.AddNewWeight(data)
        }
    }

    override fun deleteTask() {
        viewModelScope.launch {
            repository.delete(data.weight)
            _addEditWeightUIState.update {
                AddEditWeightUIState.WeightDeleted
            }
        }

    }

    override fun saveWeight() {
        if(!validateRecord()) return
            viewModelScope.launch {
                data.weight.userId = authRepository.getCurrentUserId()
                if(data.weight.id != null){
                    repository.update(data.weight)
                }
                else{
                    repository.insert(data.weight)
                }

                _addEditWeightUIState.update {
                    AddEditWeightUIState.RecordSaved
                }
            }


    }

    private fun validateRecord() : Boolean {
        var hashError = false

        if (data.weight.vaha <= 0.0){
            data.weightError = R.string.Error_pole
            hashError = true
        }
        else{
            data.weightError = null
        }

        _addEditWeightUIState.update {
            AddEditWeightUIState.AddNewWeight(data)
        }

        return !hashError
    }

    fun loadWeight(id: Long?){
        if (id!= null){
            viewModelScope.launch{
                data.weight = repository.getWeight(id)
                _addEditWeightUIState.update {
                    AddEditWeightUIState.WeightChanged(data)
                }
            }
        }
        else{
            _addEditWeightUIState.update {
                AddEditWeightUIState.WeightChanged(data)
            }
        }
    }



}
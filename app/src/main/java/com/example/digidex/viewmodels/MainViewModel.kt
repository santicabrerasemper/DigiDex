package com.example.digidex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digidex.models.Digimon
import com.example.digidex.repositories.DigimonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DigimonViewModel(private val repository: DigimonRepository) : ViewModel() {

    private val _digimonList = MutableStateFlow<List<Digimon>>(emptyList())
    val digimonList: StateFlow<List<Digimon>> get() = _digimonList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private fun <T> executeApiCall(
        onSuccess: (T) -> Unit,
        apiCall: suspend () -> T
    ) {
        viewModelScope.launch {
            try {
                val result = apiCall()
                onSuccess(result)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun loadAllDigimon() {
        executeApiCall(
            onSuccess = { _digimonList.value = it },
            apiCall = { repository.fetchAllDigimon() }
        )
    }

    fun searchDigimon(name: String) {
        executeApiCall(
            onSuccess = { _digimonList.value = it },
            apiCall = { repository.searchDigimonByName(name) }
        )
    }

    fun loadDigimonByLevel(level: String) {
        executeApiCall(
            onSuccess = { _digimonList.value = it },
            apiCall = { repository.fetchDigimonByLevel(level) }
        )
    }
}


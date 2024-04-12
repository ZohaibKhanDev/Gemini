package com.example.geminiai.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository):ViewModel() {
    private val _allGemini=MutableStateFlow<ResultState<Gemini>>(ResultState.Loading)
    val allGemini:StateFlow<ResultState<Gemini>> =_allGemini.asStateFlow()

    fun getAllGemini(content: String){
        viewModelScope.launch {
            _allGemini.value=ResultState.Loading
            try {
                val response=repository.getAllGemini(content)
                _allGemini.value=ResultState.Success(response)
            }catch (e:Exception){
                _allGemini.value=ResultState.Error(e)
            }
        }
    }
}
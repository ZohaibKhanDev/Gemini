package com.example.geminiai.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geminiai.database.Chat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository):ViewModel() {
    private val _allGemini=MutableStateFlow<ResultState<Gemini>>(ResultState.Loading)
    val allGemini:StateFlow<ResultState<Gemini>> =_allGemini.asStateFlow()

    private val _allChat=MutableStateFlow<ResultState<List<Chat>>>(ResultState.Loading)
    val allChat:StateFlow<ResultState<List<Chat>>> = _allChat.asStateFlow()
    private val _allInsert=MutableStateFlow<ResultState<Unit>>(ResultState.Loading)
    val allInsert:StateFlow<ResultState<Unit>> = _allInsert.asStateFlow()


    fun getAllChat(){
        viewModelScope.launch {
            _allChat.value=ResultState.Loading
            try {
                val response=repository.getAllChat()
                _allChat.value=ResultState.Success(response)
            }catch (e:Exception){
                _allChat.value=ResultState.Error(e)
            }
        }
    }
    fun getAllInsert(chat: Chat){
        viewModelScope.launch {
            _allInsert.value=ResultState.Loading
            try {
                val response=repository.Insert(chat)
                _allInsert.value=ResultState.Success(response)
            }catch (e:Exception){
                _allInsert.value=ResultState.Error(e)
            }
        }
    }
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
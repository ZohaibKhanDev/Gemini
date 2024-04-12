package com.example.geminiai.secondscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.Room
import com.example.geminiai.api.MainViewModel
import com.example.geminiai.api.Repository
import com.example.geminiai.database.Chat
import com.example.geminiai.database.DataBase

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SecondScreen(navController: NavController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        DataBase::class.java,
        "demo.db"
    ).allowMainThreadQueries()
        .build()
    var textField by remember {
        mutableStateOf("")
    }
    val repository = remember {
        Repository(db)
    }
    val viewModel = remember {
        MainViewModel(repository)
    }

    val chatData = remember {
        mutableStateOf<List<Chat>?>(null)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "History")
            })
        }
    ) {
        LazyColumn {

        }
    }
}

@Composable
fun ChatItem(chat: Chat) {
    Card(modifier = Modifier
        .width(340.dp)
        .height(354.dp), elevation = CardDefaults.cardElevation(3.dp)) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = chat.tittle)
        }
    }
}
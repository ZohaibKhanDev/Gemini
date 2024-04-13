package com.example.geminiai.secondscreen

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.room.Room
import com.example.geminiai.api.Gemini
import com.example.geminiai.api.MainViewModel
import com.example.geminiai.api.Repository
import com.example.geminiai.api.ResultState
import com.example.geminiai.database.Chat
import com.example.geminiai.database.DataBase
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
    var isChat by remember {
        mutableStateOf(false)
    }
    var chatData by remember {
        mutableStateOf<List<Chat>?>(null)
    }
    LaunchedEffect(key1 = isChat) {
        viewModel.getAllChat()
    }
    val state by viewModel.allChat.collectAsState()
    when (state) {
        is ResultState.Error -> {
            isChat = false
            val error = (state as ResultState.Error).error
            Text(text = error.toString())
        }

        ResultState.Loading -> {
            isChat = true
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }

        is ResultState.Success -> {
            isChat = false
            val success = (state as ResultState.Success).response
            chatData = success
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "History", color = Color.White)
            }, colors = TopAppBarDefaults.topAppBarColors(Color(0XFF001f52)))
        }
    ) {
        if (isChat) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            chatData?.let { chat ->
                items(chat) { item ->
                    ChatItem(chat = item)
                }
            }
        }
    }
}


@Composable
fun ChatItem(chat: Chat) {
    val context= LocalContext.current
    val clipboardManager = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    Card(
        modifier = Modifier
            .width(340.dp)
            .height(354.dp)
            .padding(10.dp), elevation = CardDefaults.cardElevation(3.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(imageVector = Icons.Default.CopyAll, contentDescription = "", modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .size(24.dp)
                .padding(top = 5.dp, end = 5.dp)
                .clickable {
                    clipboardManager?.setPrimaryClip(
                        android.content.ClipData.newPlainText(
                            null,
                            chat.bot
                        )
                    )
                    Toast
                        .makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT)
                        .show()
                })
            val date = timestampToTime(chat.date.toLong())
            Text(text = date, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 4.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize)
            SelectionContainer {


                Text(
                    text = chat.bot, modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 12.dp)
                )
            }

        }

    }
}
fun timestampToTime(timestamp: Long): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val instant = Instant.ofEpochMilli(timestamp)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return localDateTime.format(formatter)
    } else {

        return "No data Found"
    }
}
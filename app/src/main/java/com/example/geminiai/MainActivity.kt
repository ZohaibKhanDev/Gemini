package com.example.geminiai

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.room.Room
import com.example.geminiai.api.Gemini
import com.example.geminiai.api.MainViewModel
import com.example.geminiai.api.Part
import com.example.geminiai.api.Repository
import com.example.geminiai.api.ResultState
import com.example.geminiai.database.Chat
import com.example.geminiai.database.DataBase
import com.example.geminiai.navigation.Navigation
import com.example.geminiai.navigation.Screen
import com.example.geminiai.ui.theme.GeminiAITheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            GeminiAITheme {
                Navigation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPostResume() {
        super.onPostResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
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
    var isGemini by remember {
        mutableStateOf(false)
    }
    var geminiData by remember {
        mutableStateOf<Gemini?>(null)
    }
    val state by viewModel.allGemini.collectAsState()
    when (state) {
        is ResultState.Error -> {
            isGemini = false
            val error = (state as ResultState.Error).error
            Text(text = error.toString())
        }

        ResultState.Loading -> {

        }

        is ResultState.Success -> {
            isGemini = false
            val success = (state as ResultState.Success).response
            geminiData = success
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "WappGPT",
                    color = Color(0XFF3369FF),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 9.dp, start = 15.dp)
                )


                val anotated = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 50.sp)) {
                        append(".")

                    }
                    withStyle(style = SpanStyle(fontSize = 14.sp)) {
                        append("Online")
                    }

                }
                Text(
                    text = anotated,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 3.dp, start = 15.dp)
                )


            },
                colors = TopAppBarDefaults.topAppBarColors(Color(0XFF5A189A)),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.no),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .width(30.dp)
                            .height(42.dp)
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.FileUpload,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.clickable { navController.navigate(Screen.Second.route) }
                    )
                })
        },
        bottomBar = {
            BottomAppBar(
                windowInsets = WindowInsets(top = 13.dp, bottom = 13.dp)
            ) {
                OutlinedTextField(
                    value = textField,
                    onValueChange = {
                        textField = it

                    },
                    placeholder = {
                        Text(text = "Type Your message here...")
                    },
                    shape = RoundedCornerShape(10.dp),
                    trailingIcon = {
                        Icon(imageVector = Icons.Outlined.Send,
                            contentDescription = "",
                            tint = Color(0XFF4361EE),
                            modifier = Modifier.clickable {
                                isGemini = true
                                viewModel.getAllGemini(
                                    textField
                                )
                                textField = ""
                            })
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)

                )
            }
        }
    ) { it ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding()),
            color = MaterialTheme.colorScheme.background
        ) {


            if (isGemini) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 35.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(bottom = it.calculateBottomPadding())
                ) {
                    geminiData?.candidates?.forEach { candidate ->
                        items(candidate.content.parts) { part ->
                            GeminiItem(part = part)

                            Box(
                                modifier = Modifier.padding(start = 25.dp, bottom = 20.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.chatlogo),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop, modifier = Modifier
                                        .width(11.dp)
                                        .height(17.dp)
                                )


                            }
                        }

                    }

                }
            }

        }

    }
}


@Composable
fun GeminiItem(part: Part) {
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
    val save by remember {
        mutableStateOf(false)
    }
    val allData = db.chatdao().getAllChat()
    var isAlreadyThere: Boolean? = null
    allData.forEach {
        isAlreadyThere = it.bot.contains(part.text)
    }
    if (isAlreadyThere == true) {
        return
    } else {
        val chat = Chat(null, part.text, System.currentTimeMillis().toString())
        viewModel.getAllInsert(chat)
    }

    val clipboardManager = getSystemService(context, ClipboardManager::class.java)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth()
                .padding(25.dp),
            colors = CardDefaults.cardColors(Color(0XFFF0F4F9)),
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp, bottomEnd = 25.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(imageVector = Icons.Default.ContentCopy,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 10.dp, end = 11.dp)
                        .size(19.dp)
                        .clickable {
                            clipboardManager?.setPrimaryClip(
                                android.content.ClipData.newPlainText(
                                    null,
                                    part.text
                                )
                            )
                            Toast
                                .makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT)
                                .show()

                        })
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp), contentAlignment = Alignment.Center
            ) {

                SelectionContainer {
                    Text(
                        text = part.text, fontSize = 15.sp, modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.Center)
                    )
                }
            }

        }
    }
}





package com.example.geminiai.detailscreen

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController


@Composable
fun DetailScreen(navController: NavController, text: String?) {
    val context = LocalContext.current
    val clipboardManager = ContextCompat.getSystemService(context, ClipboardManager::class.java)



    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp, start = 3.dp, end = 0.dp),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, start = 10.dp, end = 3.dp), contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.CopyAll,
                        contentDescription = "",
                        modifier = Modifier
                            .align(
                                Alignment.TopEnd
                            ).padding( bottom = 18.dp, start = 35.dp)
                            .size(20.dp)

                            .clickable {
                                clipboardManager?.setPrimaryClip(
                                    ClipData.newPlainText(
                                        null,
                                        text
                                    )
                                )
                                Toast
                                    .makeText(
                                        context,
                                        "Text copied to clipboard",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            })
                    Text(text = text.toString())
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 1.dp, top = 8.dp, end = 299.dp)
                .clickable { navController.popBackStack() },
        )
    }
}
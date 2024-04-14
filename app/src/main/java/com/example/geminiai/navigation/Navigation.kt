package com.example.geminiai.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.geminiai.HomeScreen
import com.example.geminiai.detailscreen.DetailScreen
import com.example.geminiai.secondscreen.SecondScreen

@Composable
fun Navigation() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination =Screen.Home.route ){
        composable(Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(Screen.Second.route){
            SecondScreen(navController = navController)
        }
        composable(Screen.Detail.route +"/{text}",
            arguments = listOf(
                navArgument("text"){
                    type= NavType.StringType
                }
            )
            ){
            val text=it.arguments?.getString("text")
            DetailScreen(navController,text)
        }
    }
}
sealed class Screen(
    val route:String
){
    object Home:Screen("Home")
    object Second:Screen("Second")
    object Detail:Screen("Detail")
}



package com.example.m7019e_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.m7019e_lab1.ui.theme.M7019E_Lab1Theme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.m7019e_lab1.ui.MovieList


enum class MovieAppScreen(@StringRes val title: Int) {
    Welcome(title = R.string.app_name),
    MovieList(title = R.string.movie_list)
}


@Composable
fun MovieAppBar() {

}

@Composable
fun MovieAppNavigator(
    navController: NavHostController = rememberNavController()
) {
    Scaffold (

    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = MovieAppScreen.Welcome.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MovieAppScreen.Welcome.name) {

            }
            composable(route = MovieAppScreen.MovieList.name) {
                MovieList()
            }
        }
    }
}



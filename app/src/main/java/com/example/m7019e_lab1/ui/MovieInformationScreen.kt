package com.example.m7019e_lab1.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MovieInformation(navController: NavHostController, movieTitle: String?) {
    Text(text = movieTitle ?: "Movie Details")
}


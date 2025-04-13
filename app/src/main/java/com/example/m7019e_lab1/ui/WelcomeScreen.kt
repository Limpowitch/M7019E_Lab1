package com.example.m7019e_lab1.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.m7019e_lab1.MovieAppScreen
import androidx.compose.foundation.layout.size

@Composable
fun Welcome(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                navController.navigate(MovieAppScreen.MovieList.name)
            },
            modifier = Modifier.size(width = 200.dp, height = 60.dp)
        ) {
            Text(text = "Browse Movies")
        }
    }

}


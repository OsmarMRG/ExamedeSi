package com.example.bibliotecaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bibliotecaapp.screens.BookSearchScreen
import com.example.bibliotecaapp.screens.HomeScreen
import com.example.bibliotecaapp.screens.ReservationHistoryScreen

@Composable
fun BibliotecaNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToSearch = { navController.navigate("search") },
                onNavigateToHistory = { navController.navigate("history") }
            )
        }
        
        composable("search") {
            BookSearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToHistory = { navController.navigate("history") }
            )
        }
        
        composable("history") {
            ReservationHistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
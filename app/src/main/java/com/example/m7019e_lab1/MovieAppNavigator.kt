package com.example.m7019e_lab1

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.m7019e_lab1.ui.theme.M7019E_Lab1Theme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.m7019e_lab1.ui.MovieInformation
import com.example.m7019e_lab1.ui.Welcome
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.m7019e_lab1.viewmodel.MoviesViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.m7019e_lab1.ui.MovieGrid
import com.example.m7019e_lab1.data.workers.FetchMoviesWorker
import com.example.m7019e_lab1.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf


enum class MovieAppScreen(@StringRes val title: Int) {
    Welcome(title = R.string.app_name),
    MovieList(title = R.string.movie_list),
    MovieInformation(title = R.string.movie_information),
    MovieReviews(title = R.string.movie_reviews),
    TopGrid(title = R.string.top_grid),
    PopularGrid(title = R.string.popular_grid)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(
    currentScreen: MovieAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
    customTitle: String? = null
) {
    TopAppBar(
        title = { Text(text = customTitle ?: stringResource(id = currentScreen.title)) },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}

@Composable
fun MovieAppNavigator(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val moviesViewModel: MoviesViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    )

    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route ?: MovieAppScreen.Welcome.name
    val currentScreenName = route.substringBefore("/")
    val currentScreen = MovieAppScreen.valueOf(currentScreenName)


    val category = when (currentScreen) {                                               // <-- Here
        MovieAppScreen.TopGrid     -> FetchMoviesWorker.CAT_TOP
        MovieAppScreen.PopularGrid -> FetchMoviesWorker.CAT_POPULAR
        else                       -> null
    }

    val emptyMoviesFlow = remember { MutableStateFlow<List<Movie>>(emptyList()) }

    val moviesFlow: Flow<List<Movie>> = if (category != null) {
        moviesViewModel.moviesByCategoryOrFetch(category)
    } else {
        emptyMoviesFlow
    }                                                                                   // <-- To here
                                                                                        // Makes sure that we retrieve the correct category of movies

    val selectedMovie by moviesViewModel
        .selectedMovie
        .collectAsState(initial = null)

    val customTitle: String? = when (currentScreen) {           // Just a custom title value we use in MovieInformationScreen
        MovieAppScreen.MovieInformation -> selectedMovie?.title
        else                            -> null
    }

    Scaffold(
        topBar = {
            MovieAppBar(
                currentScreen = currentScreen,
                canNavigateBack = currentScreen != MovieAppScreen.Welcome,
                navigateUp = { navController.navigateUp() },
                customTitle = customTitle
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieAppScreen.Welcome.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MovieAppScreen.Welcome.name) {
                Welcome(navController)
            }

            composable(route = MovieAppScreen.TopGrid.name) {
                MovieGrid(navController = navController,  category = FetchMoviesWorker.CAT_TOP, moviesFlow = moviesFlow as StateFlow<List<Movie>>)
            }

            composable(route = MovieAppScreen.PopularGrid.name) {
                MovieGrid(navController = navController,  category = FetchMoviesWorker.CAT_POPULAR, moviesFlow = moviesFlow as StateFlow<List<Movie>>)
            }

            composable(
                    route = "${MovieAppScreen.MovieInformation.name}/{movieId}",
                    arguments = listOf(navArgument("movieId") { type = NavType.LongType })
                        ) { backStackEntry ->
                    MovieInformation(
                            navController       = navController,
                            movieId             = backStackEntry.arguments?.getLong("movieId")?.toString(),
                            moviesViewModel     = moviesViewModel
                                )
            }
        }
    }
}



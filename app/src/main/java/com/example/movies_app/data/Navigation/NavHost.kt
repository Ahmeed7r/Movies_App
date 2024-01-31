package com.example.movies_app.data.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies_app.Home.Details_Screen
import com.example.movies_app.Home.HomeScreen
import com.example.movies_app.Home.Splash


@Composable
fun NavHost() {
    val navcontroller = rememberNavController()

    NavHost(
        navController = navcontroller,
        startDestination = "splash"
    ) {
        composable("splash") {

            Splash(navcontroller = navcontroller)
        }
        composable("main") {
             HomeScreen(navcontroller)
        }
        composable("details/{title}/{overview}/{popularity}/{image}/{vote}/{release}") {
            val title = it.arguments?.getString("title")
            val overview = it.arguments?.getString("overview")
            val popularity = it.arguments?.getDouble("popularity")
            val image = it.arguments?.getString("image")
            val vote = it.arguments?.getDouble("vote")
            val release = it.arguments?.getString("release")
           Details_Screen(title.toString(),overview.toString(),popularity!!.toDouble(),image.toString(),vote!!.toDouble(),release.toString())
        }
    }
}
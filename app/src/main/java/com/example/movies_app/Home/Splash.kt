package com.example.movies_app.Home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.movies_app.R
import kotlinx.coroutines.delay

@Composable
fun Splash(navcontroller:NavController){
    LaunchedEffect(key1 = true){
        delay(3000)
        navcontroller.popBackStack()
        navcontroller.navigate("main")
    }

    LottieExample()
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LottieExample() {

    // to keep track if the animation is playing
    // and play pause accordingly
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
        .background(
            Color.Black
        )
        .fillMaxSize()) {

        // for speed
        var speed by remember {
            mutableStateOf(1f)
        }








        // remember lottie composition ,which
        // accepts the lottie composition result
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.movies)
        )


        // to control the animation
        val progress by animateLottieCompositionAsState(
            // pass the composition created above
            composition,

            // Iterates Forever
            iterations = LottieConstants.IterateForever,



            // pass speed we created above,
            // changing speed will increase Lottie
            speed = speed


        )
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.size(400.dp)
        )




    }




}

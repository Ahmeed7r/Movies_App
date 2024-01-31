package com.example.movies_app.Home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.fonts.Font
import android.graphics.fonts.FontFamily
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movies_app.data.Navigation.data_object
import com.example.movies_app.data.Navigation.movies_data
import com.example.movies_app.model.Server
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun HomeScreen(navController: NavController) {
    var context = LocalContext.current.applicationContext
    var search by remember {
        mutableStateOf("")
    }
    var List_of_Movies:MutableList<data_object> by remember {
       mutableStateOf(mutableListOf())
    }
    var state by remember {
        mutableStateOf(false)
    }
    var List_Search:MutableList<data_object> by remember {
        mutableStateOf(mutableListOf())
    }
    if(state==false){
        GlobalScope.launch (Dispatchers.IO){
            // List_of_Movies= Server.Get_Api().Movies()
            Server.Get_Api().Movies().enqueue(object :Callback<movies_data>{
                override fun onResponse(call: Call<movies_data>, response: Response<movies_data>) {
                    response.body()!!.results.forEach {
                        List_of_Movies.add(data_object(it.title,it.poster_path,it.overview,it.popularity,it.vote_average,it.release_date))

                    }
                    state=true
                    // Toast.makeText(context,response.body().toString(),Toast.LENGTH_LONG).show()

                }

                override fun onFailure(call: Call<movies_data>, t: Throwable) {

                }

            })
            delay(2000)

        }
    }

        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), horizontalAlignment = Alignment.CenterHorizontally,) {
            TopAppBar(modifier = Modifier
                .padding(14.dp)
                .background(Color.Black)
                .clip(RoundedCornerShape(25.dp))
                ,title = {
                OutlinedTextField(value = search, onValueChange = {
                    search = it
                }, trailingIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "email icon")

                    }
                }, label = {
                    Text(text = "Search")
                }, modifier = Modifier.padding(14.dp))
            })
            if(state){
                if (search.isNotEmpty()){
                    List_Search.clear()

                  List_of_Movies.forEach {
                      if(it.title.toLowerCase().startsWith(search.toLowerCase())){
                          if(List_Search.contains(data_object(it.title,it.image,it.overview,it.popularity,it.vote_average,it.release_date))){

                          }else{
                              List_Search.add(data_object(it.title,it.image,it.overview,it.popularity,it.vote_average,it.release_date))
                          }

                      }

                  }


                    Show_Search_Moview(list = List_Search,navController)
                }
                if(search.isEmpty()){
                    if(List_Search.isNotEmpty() && List_Search!=null){
                        List_Search.clear()
                    }

                    Show_Item(list = List_of_Movies, search =search,navController )

                }

            }else{
                animations()
            }




        }




}




@Composable
fun Show_Item(list:List<data_object>,search:String,navController: NavController){
    var context= LocalContext.current.applicationContext

    LazyColumn() {
        items(list) {
            Box( modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable {

                    navController.navigate(
                        "details/${it.title}/${it.overview}/${it.popularity}/${
                            it.image
                                .drop(1)
                                .dropLast(3)
                        }/${it.vote_average}/${it.release_date}"
                    )

                }
                .background(Color.Gray.copy(alpha = 0.6f))
//                .shadow(4.dp, shape = RoundedCornerShape(15.dp))
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .padding(5.dp)
                        .background(Color(0xFFF4F4F4)),
                    horizontalAlignment = Alignment.CenterHorizontally) {


                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w500${it.image}")
                            .build(),
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                        contentScale = ContentScale.Crop
                    )
                    /*    Image(
                            painter = rememberImagePainter(data = it.image
                                 , builder = {

                                    error(Color.Red)
                                }
                            ),
                            contentDescription = "project",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                            contentScale = ContentScale.Crop,
                        )*/
                        Text(
                            text = it.title,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Black
                            ,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp, end = 10.dp),
                            textAlign = TextAlign.Right
                        )

                    }

                }
            }

        }

}

@Composable
fun Show_Search_Moview(list:List<data_object>,navController: NavController){
    if(list.isEmpty()){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(imageVector = Icons.Filled.Search, contentDescription = "search", modifier = Modifier.background(
                Color.White))
            Spacer(modifier = Modifier.padding(3.dp))
            Text(text = "No Result ...", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color.White)
        }

    }
    LazyColumn() {
        items(list) {
            Box( modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable {

                    navController.navigate(
                        "details/${it.title}/${it.overview}/${it.popularity}/${
                            it.image
                                .drop(1)
                                .dropLast(3)
                        }/${it.vote_average}/${it.release_date}"
                    )

                }
                .background(Color.Gray.copy(alpha = 0.6f))
//                .shadow(4.dp, shape = RoundedCornerShape(15.dp))
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .padding(5.dp)
                        .background(Color(0xFFF4F4F4)),
                    horizontalAlignment = Alignment.CenterHorizontally) {


                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w500${it.image}")
                            .build(),
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                        contentScale = ContentScale.Crop
                    )
                    /*    Image(
                            painter = rememberImagePainter(data = it.image
                                 , builder = {

                                    error(Color.Red)
                                }
                            ),
                            contentDescription = "project",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                            contentScale = ContentScale.Crop,
                        )*/
                    Text(
                        text = it.title,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Black
                        ,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp, end = 10.dp),
                        textAlign = TextAlign.Right
                    )

                }

            }
        }

    }

}

@Composable
fun animations() {
    val scolor = listOf(
        Color.Gray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.2f),
        Color.Gray.copy(alpha = 0.6f)
    )
    val transiction = rememberInfiniteTransition(label = "")
    val translate = transiction.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing

            )
        ), label = ""
    )
    val brush = Brush.linearGradient(
        colors = scolor,
        start = Offset.Zero,
        end = Offset(x = translate.value, y = translate.value)
    )
    Shimer(brush = brush)
}

@Composable
fun Shimer(brush: Brush) {
    LazyColumn(){
        items(7){
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(15.dp)),
                    //elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    //  colors = CardDefaults.cardColors(containerColor = Color.White),


                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(brush)
                        )


                    }
                }
            }
        }
    }


}



package com.example.movies_app.Home

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File

@Composable
fun Details_Screen(title:String,overview:String, popularity: Double,image:String,vote:Double,release:String){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        var context= LocalContext.current.applicationContext

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500/${image}jpg")
                .build(),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(6.dp)
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
            contentScale = ContentScale.Crop
        )
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = "popularity : ${popularity.toString()}",
                fontSize = 15.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Black
                ,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 5.dp, end = 10.dp),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Image(imageVector = Icons.Filled.DateRange, contentDescription = "date")
            Text(
                text = release.toString(),
                fontSize = 15.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Black
                ,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 5.dp, end = 10.dp),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Image(imageVector = Icons.Default.Favorite, contentDescription = "vote")

            Text(
                text = "vote : ${vote.toString()}",
                fontSize = 15.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Black
                ,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 5.dp, end = 10.dp),
                textAlign = TextAlign.Right
            )

        }

        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Black
            ,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 10.dp, end = 10.dp),
            textAlign = TextAlign.Right
        )

        Text(
            text = overview,
            fontSize = 14.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 10.dp, end = 10.dp),
            textAlign = TextAlign.Right
        )

    }

}
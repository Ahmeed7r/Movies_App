package com.example.movies_app.data.Navigation

import retrofit2.Call
import retrofit2.http.GET

interface Api {
@GET("movie/popular?api_key=bc83bec557bc106230cafb2048bfa36e")
fun Movies():Call<movies_data>
}
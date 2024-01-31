package com.example.movies_app.model

import com.example.movies_app.data.Navigation.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Server {

        var apiserver:Api?=null
        fun Get_Api(): Api {
            if(apiserver==null){
                apiserver= Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api::class.java)


            }
            return apiserver!!
        }

}
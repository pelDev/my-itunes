package com.peldev.myitunes

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MusicSearchService {
    @GET("search")
    suspend fun getMusicList(
            @Query("term") artisteName: String) : String
}


private val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://itunes.apple.com/")
            .build()



object MusicApi {
    val retrofitService: MusicSearchService by lazy {
        retrofit.create(MusicSearchService::class.java)
    }
}


package com.wolfython.randommemegenerator.Api


import com.wolfython.randommemegenerator.data.MemeImageData
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {
    @GET("/meme")
    suspend fun MemeGet(): Response<MemeImageData>
}
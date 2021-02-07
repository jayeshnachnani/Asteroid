package com.udacity.asteroidradar.api


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov/planetary/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



// TODO (03) Implement the AsteroidApiService interface with @GET getProperties returning a String

interface ImageApiService {
    //retrofit will generate the functions required, we just need to tell it what to do
    @GET("apod?api_key=50eCWN0C6HrKmxnencJcxAJMfeUXbCGjSKuuE0iZ")
    //fun getProperties(@Query("start_date") String startDate):
    fun getProperties():
            Call <PictureOfDay>
}


// TODO (04) Create the MarsApi object using Retrofit to implement the MarsApiService

object ImageApi {
    val retrofitService : ImageApiService by lazy {
        retrofit.create(ImageApiService::class.java)
    }
}


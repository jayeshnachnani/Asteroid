package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"
val nextsevendays = getNextSevenDaysFormattedDates()

val today = nextsevendays.first()
val lastdayofweek = nextsevendays.last()
//end date
enum class AsteroidApiFilter(val value: String) { VIEW_WEEK(lastdayofweek), VIEW_TODAY(today), VIEW_SAVED(lastdayofweek) }

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

// TODO (03) Implement the AsteroidApiService interface with @GET getProperties returning a String

interface AsteroidApiService {
    @GET("feed?api_key=50eCWN0C6HrKmxnencJcxAJMfeUXbCGjSKuuE0iZ")
    fun getProperties(@Query("start_date") date: String = today, @Query("end_date") type: String = lastdayofweek):
            Call <String>
}

object AsteroidApi {
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}


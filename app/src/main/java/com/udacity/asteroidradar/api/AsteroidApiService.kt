package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.RawJson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import com.udacity.asteroidradar.main.MainViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber

private const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/"
val nextsevendays = getNextSevenDaysFormattedDates()

val today = nextsevendays.first()

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

//val String startDate= "2020-01-01"
private const val endDate= "2020-01-02"


// TODO (03) Implement the AsteroidApiService interface with @GET getProperties returning a String

/*interface AsteroidApiService {
    //retrofit will generate the functions required, we just need to tell it what to do
    //@GET("feed?start_date={today}&end_date={today}&api_key=50eCWN0C6HrKmxnencJcxAJMfeUXbCGjSKuuE0iZ")
    @GET("feed?start_date=2021=07-02&api_key=50eCWN0C6HrKmxnencJcxAJMfeUXbCGjSKuuE0iZ")
    //fun getProperties(@Query("start_date") String startDate):
    fun getProperties():
    //fun getProperties(@Path("today") date: String = today):
            Call <String>
}*/

interface AsteroidApiService {
    //retrofit will generate the functions required, we just need to tell it what to do
    //@GET("feed?start_date={today}&api_key=50eCWN0C6HrKmxnencJcxAJMfeUXbCGjSKuuE0iZ")
    @GET("feed?api_key=50eCWN0C6HrKmxnencJcxAJMfeUXbCGjSKuuE0iZ")
    //fun getProperties(@Query("start_date") String startDate):
    fun getProperties(@Query("start_date") date: String = today):
            Call <String>
}


// TODO (04) Create the MarsApi object using Retrofit to implement the MarsApiService

object AsteroidApi {
    val retrofitService : AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}


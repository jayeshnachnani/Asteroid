package com.udacity.asteroidradar.work

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class FetchDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "FetchDataWorker"
    }
    val dataSource = AsteroidDatabase.getInstance(applicationContext).asteroidDatabaseDao
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    /*override suspend fun doWork(): Result {
        //TODO("Not yet implemented")
        //val dataSource = AsteroidDatabase.getInstance(applicationContext).asteroidDatabaseDao
        //val database = getDatabase(applicationContext)
        //val repository = VideosRepository(database)
        return try {
            dataSource.getAllAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }*/

    override suspend fun doWork(): Result {
        return try {
            val response = AsteroidApi.retrofitService.getProperties()
            //if response.

            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
        /*AsteroidApi.retrofitService.getProperties().enqueue( object: Callback<String> {
            override  fun onFailure(call: Call<String>, t: Throwable) {
                //_response.value = "Failure: " + t.message
                //Timber.i("Failure:" + _response.value)
                Result.failure()
            }

            override  fun onResponse(call: Call<String>, response: Response<String>) {
                //_response.value = "Success: ${response.body()} Asteroid properties retrieved"
                var obj1 = JSONObject(response.body().toString())
                Timber.i("obj1:" + obj1.toString())
                var asteroidList = ArrayList<Asteroid>()
                asteroidList = parseAsteroidsJsonResult(obj1)
                asteroidList.forEach {  "Timbuk:" + Timber.i("TimbukTimbuk" + it.codename) }
                asteroidList.forEach {
                    //dataSource.insert(it)
                    //_list.add(it)
                }
                coroutineScope.launch {
                    withContext(Dispatchers.IO)
                    {
                        //dataSource.insert(asteroid)
                    test()}
                }
                //viewModelScope.launch {
                    //test()
                    //insertAsteroidsToDatabase()
                //}
                val asteroid1 = Asteroid(1L,"second","sec",1.6,1.7,
                    1.8,1.9,true)
                //asteroidList.add(asteroid1)
                /*dataSource.insert(asteroid1)
                Timber.i("Data inserted")
                val asterList = dataSource.getAllAsteroids()
                asterList.value?.forEach {"Timbuk2:" + Timber.i(it.codename) }*/
              Result.success()
            }
        })


        //_response.value = "Set the Mars API Response here!"
    }*/

    private suspend fun test() {
        withContext(Dispatchers.IO) {
            //dataSource.insert(asteroid)

        }

    }
}
package com.udacity.asteroidradar.work

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.AsteroidApiFilter
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

class FetchDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "FetchDataWorker"
    }

    val dataSource = AsteroidDatabase.getInstance(applicationContext).asteroidDatabaseDao
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    var asteroidList = ArrayList<Asteroid>()


    override suspend fun doWork(): Result {
        return try {
            var filter: AsteroidApiFilter = AsteroidApiFilter.VIEW_SAVED
            val response: Response<String> = AsteroidApi.retrofitService.getProperties(type=filter.value).execute()
            var obj1 = JSONObject(response.body().toString())
            asteroidList = parseAsteroidsJsonResult(obj1)
            coroutineScope.launch {
                withContext(Dispatchers.IO)
                {
                    asteroidList.forEach { dataSource.insert(it) }
                    Timber.i("Data inserted2!!!")

                }
            }
            Timber.i("Data inserted3!!!")
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
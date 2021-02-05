package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import retrofit2.HttpException

class FetchDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "FetchDataWorker"
    }

    override suspend fun doWork(): Result {
        //TODO("Not yet implemented")
        val dataSource = AsteroidDatabase.getInstance(applicationContext).asteroidDatabaseDao
        //val database = getDatabase(applicationContext)
        //val repository = VideosRepository(database)
        return try {
            dataSource.getAllAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
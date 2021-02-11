package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.work.*
import com.udacity.asteroidradar.work.FetchDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application(){

    val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        //Timber.plant(Timber.DebugTree())
        delayedInit()
    }

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
        Timber.i("Data inserted0!!!")
    }

    private fun setupRecurringWork() {
        Timber.i("Data inserted1!!!")

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<FetchDataWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(FetchDataWorker.WORK_NAME)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            FetchDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )

        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData(FetchDataWorker.WORK_NAME)
            .observe(this,
                Observer { workInfo ->
                    if (workInfo?.get(0)?.state == WorkInfo.State.SUCCEEDED) {
                        Timber.i("JOB COMPLETED SUCCESSFULLY")
                    }
                })
    }
}

private fun <T> LiveData<T>.observe(asteroidRadarApplication: AsteroidRadarApplication, observer: Observer<T>) {

}


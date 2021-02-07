package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.ImageApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel(val database: AsteroidDatabaseDao,
                    application: Application
) : AndroidViewModel(application) {


    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //private val asteroids = database.getAllAsteroids()
    //val asteroids = database.getAllAsteroids()
    val asteroid1 = Asteroid(1L,"dummy","dummy",1.6,1.7,
        1.8,1.9,false)
    private val _list = mutableListOf<Asteroid>()
    private val _asteroidTempList = MutableLiveData<List<Asteroid>>()
    val asteroidTempList: LiveData<List<Asteroid>>
        get() = _asteroidTempList
    var obj3 = PictureOfDay("","","")

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails
        get() = _navigateToAsteroidDetails

    private var _imgURL : String = ""
    val imgURL: String
        get() =_imgURL

    val str = Timber.i("try")
    val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao

    init {
        getAsteroidProperties()
        getImage()
        addtoList()
        Timber.i("Image1:" + imgURL.toString())
        Timber.i("Image2:" + _imgURL.toString())

        _list.add(asteroid1)

    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onAsteroidNavigated() {
        _navigateToAsteroidDetails.value = null
    }


    private fun getAsteroidProperties() {
        AsteroidApi.retrofitService.getProperties().enqueue( object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                _response.value = "Failure: " + t.message
                Timber.i("Failure:" + _response.value)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                _response.value = "Success: ${response.body()} Asteroid properties retrieved"
                Timber.i("Response2:" + response.body())
                var obj1 = JSONObject(response.body().toString())
                Timber.i("obj1:" + obj1.toString())
                var asteroidList = ArrayList<Asteroid>()
                asteroidList = parseAsteroidsJsonResult(obj1)
                asteroidList.forEach {  "Timbuk:" + Timber.i("TimbukTimbuk" + it.codename) }
                asteroidList.forEach {
                    //dataSource.insert(it)
                _list.add(it)
                }
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        insertAsteroidsToDatabase()
                    }
                    //insertAsteroidsToDatabase()
                }
                val asteroid1 = Asteroid(1L,"second","sec",1.6,1.7,
                    1.8,1.9,true)

            }
        })

    }

    private fun getImage() {
        ImageApi.retrofitService.getProperties().enqueue( object: Callback<PictureOfDay> {
            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                _response.value = "Failure: " + t.message
                Timber.i("Failure:" + _response.value)
            }

            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                _response.value = "Success: ${response.body()} Asteroid properties retrieved"
                var obj2 =response.body()
                obj3 = response.body()!!

                Timber.i("obj3:" + obj3?.url.toString())
                Timber.i("Image2:" + imgURL.toString())
                val asteroid1 = Asteroid(1L,"second","sec",1.6,1.7,
                    1.8,1.9,true)
            }
        })

    }

    private fun addtoList(){
        val asteroidTemp1List = dataSource.getAllAsteroids()
        _list.clear()
        asteroidTemp1List.value?.forEach { _list.add (it)}
        _asteroidTempList.value = _list
    }

    private suspend fun insertAsteroidsToDatabase() {
        _list.forEach{dataSource.insert(it)}
        //_list.clear()
    }
}
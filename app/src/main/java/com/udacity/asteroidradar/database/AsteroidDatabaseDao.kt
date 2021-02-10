package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.nextsevendays

@Dao
interface AsteroidDatabaseDao {

    val today: String
        get() = nextsevendays.first()
    val lastdayofweek: String
        get() = nextsevendays.last()

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(asteroid: Asteroid)

    @Update
    fun update(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_details ORDER BY id DESC" )
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_details where asteroid_details.close_approach_date >= date('now') and asteroid_details.close_approach_date <= date('now') ORDER BY id DESC LIMIT 5" )
    fun getTodaysAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_details where asteroid_details.close_approach_date >= date('now') and asteroid_details.close_approach_date <= date('now'+ '7 day') ORDER BY id DESC LIMIT 7" )
    fun getWeeklyAsteroids(): LiveData<List<Asteroid>>

}
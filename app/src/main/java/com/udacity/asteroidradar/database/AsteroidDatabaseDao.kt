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

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(asteroids: MutableList<Asteroid>)

    @Update
    fun update(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_details ORDER BY asteroid_details.close_approach_date DESC" )
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_details where asteroid_details.close_approach_date >= date('now') and asteroid_details.close_approach_date <= date('now') ORDER BY asteroid_details.close_approach_date DESC" )
    fun getTodaysAsteroids(): LiveData<List<Asteroid>>

    //@Query("SELECT * FROM asteroid_details where asteroid_details.close_approach_date >= date('now') and asteroid_details.close_approach_date <= date('now','+7days') ORDER BY asteroid_details.close_approach_date DESC LIMIT 6" )
    @Query("SELECT * FROM asteroid_details where asteroid_details.close_approach_date between datetime ('now') and datetime ('now', '+7 days') ORDER BY asteroid_details.close_approach_date DESC" )
    fun getWeeklyAsteroids(): LiveData<List<Asteroid>>

}
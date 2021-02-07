package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
@Dao
interface AsteroidDatabaseDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(asteroid: Asteroid)

    @Update
    fun update(asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_details ORDER BY id DESC LIMIT 2")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

}
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

    /*@Query("SELECT * from asteroid_details WHERE id = :key")
    suspend fun get(key: Long): Asteroid?

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM asteroid_details ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM asteroid_details ORDER BY id DESC LIMIT 1")
    suspend fun getLatestAsteroid(): Asteroid?

    /**
     * Selects and returns the night with given nightId.
     */
    @Query("SELECT * from asteroid_details WHERE id = :key")
    fun getAsteroidWithId(key: Long): LiveData<Asteroid>*/
}
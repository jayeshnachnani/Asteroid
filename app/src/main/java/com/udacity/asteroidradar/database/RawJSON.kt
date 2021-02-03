package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


//@Entity(tableName = "raw_json")

@JsonClass(generateAdapter = true)
data class RawJson(
    //@PrimaryKey(autoGenerate = true)
    //val id: Long,
    //@ColumnInfo(name = "code_name")
    @field:Json(name = "links")
    var links: String,
    //@ColumnInfo(name = "links")
    @field:Json(name = "closeApproachDate")
    var closeApproachDate: String,
    //@ColumnInfo(name = "element_count")
    @field:Json(name = "element_count")
    var element_count: String,
    //@ColumnInfo(name = "near_earth_objects")
    @field:Json(name = "near_earth_objects")
    var near_earth_objects: String)
package com.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val lat: Double,
    val lng: Double
)

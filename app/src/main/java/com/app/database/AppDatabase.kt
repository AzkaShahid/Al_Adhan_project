package com.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [User::class,CityDBModel::class], version = 1, exportSchema = false)
abstract class  AppDatabase : RoomDatabase() {
    abstract fun userDao(): AppDao


}

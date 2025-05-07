package com.example.tyw.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tyw.model.Weight


@Database(entities = [Weight::class], version = 4, exportSchema = true)
abstract class WeightDatabase : RoomDatabase(){

    abstract fun weightDao(): WeightDao

    companion object {

        private var INSTANCE: WeightDatabase? = null
        fun getDatabase(context: Context): WeightDatabase {
            if (INSTANCE == null) {
                synchronized(WeightDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WeightDatabase::class.java, "weight_database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
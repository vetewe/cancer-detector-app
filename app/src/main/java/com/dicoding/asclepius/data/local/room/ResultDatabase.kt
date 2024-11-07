package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dicoding.asclepius.data.local.entity.ResultEntity

@Database(entities = [ResultEntity::class], version = 2)
@TypeConverters(UriConverter::class)
abstract class ResultDatabase : RoomDatabase() {
    abstract fun ResultDao(): ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): ResultDatabase {
            if (INSTANCE == null) {
                synchronized(ResultDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ResultDatabase::class.java, "analyze_result_database"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                    }
                }
            }
            return INSTANCE as ResultDatabase
        }
    }
}

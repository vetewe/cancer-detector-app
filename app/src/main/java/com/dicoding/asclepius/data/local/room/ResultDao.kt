package com.dicoding.asclepius.data.local.room

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import com.dicoding.asclepius.data.local.entity.ResultEntity

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveResult(analyzeResultEntity: ResultEntity): Long

    @Query("SELECT * FROM ResultEntity")
    fun getAllResults(): LiveData<List<ResultEntity>>

    @Query("SELECT * FROM ResultEntity WHERE imageUri = :imageUri LIMIT 1")
    fun getResultByUri(imageUri: String): ResultEntity?

    @Delete
    fun deleteResult(analyzeResultEntity: ResultEntity)
}

class UriConverter {
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }
}
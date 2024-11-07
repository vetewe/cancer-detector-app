package com.dicoding.asclepius.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.entity.ResultEntity
import com.dicoding.asclepius.data.local.room.ResultDao
import com.dicoding.asclepius.utils.AppExecutors

class ResultRepository(
    private val resultDao: ResultDao,
    private val appExecutors: AppExecutors
) {

    fun saveAnalyzeResult(analyzeResultEntity: ResultEntity) {
        appExecutors.diskIO.execute {
            resultDao.saveResult(analyzeResultEntity)
        }
    }

    fun removeAnalyzeResult(analyzeResultEntity: ResultEntity) {
        appExecutors.diskIO.execute {
            resultDao.deleteResult(analyzeResultEntity)
        }
    }

    fun showAnalyzeResult(): LiveData<List<ResultEntity>> {
        return resultDao.getAllResults()
    }

    fun getAnalyzeResultByUri(imageUri: String, callback: (ResultEntity?) -> Unit) {
        appExecutors.diskIO.execute {
            val result = resultDao.getResultByUri(imageUri)
            appExecutors.mainThread.execute {
                callback(result)
            }
        }
    }


    companion object {

        @Volatile
        private var instance: ResultRepository? = null

        fun getInstance(
            analyzeResultDao: ResultDao,
            appExecutors: AppExecutors
        ): ResultRepository =
            instance ?: synchronized(this) {
                instance ?: ResultRepository(analyzeResultDao, appExecutors)
            }.also { instance = it }
    }
}
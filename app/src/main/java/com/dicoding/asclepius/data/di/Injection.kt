package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.ResultDatabase
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.repository.ArticleRepository
import com.dicoding.asclepius.data.repository.ResultRepository
import com.dicoding.asclepius.utils.AppExecutors

object Injection {
    fun articleRepository(): ArticleRepository {
        val apiService = ApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }

    fun resultRepository(context: Context): ResultRepository {
        val database = ResultDatabase.getInstance(context)
        val analyzeResultDao = database.ResultDao()
        val appExecutors = AppExecutors()
        return ResultRepository.getInstance(analyzeResultDao, appExecutors)
    }
}
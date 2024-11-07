package com.dicoding.asclepius.data.repository

import android.util.Log
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.remote.response.ArticleResponse
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.retrofit.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository private constructor(
    private val apiService: ApiServices
) {
    fun getArticle(errorMessageOnFailure: String, errorMessageOnResponseFail: String, callback: (Result<List<ArticlesItem?>>) -> Unit) {
        val client = apiService.getArticle(BuildConfig.API_KEY)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    if (articles.isNullOrEmpty()) {
                        callback(Result.Error(errorMessageOnFailure))
                    } else {
                        callback(Result.Success(articles))
                    }
                } else {
                    callback(Result.Error("$errorMessageOnResponseFail: ${response.message()}"))
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                callback(Result.Error("$errorMessageOnFailure: ${t.message}"))
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ArticleRepository"

        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(
            apiService: ApiServices
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
            }.also { instance = it }
    }
}
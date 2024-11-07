package com.dicoding.asclepius.view.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.repository.ArticleRepository

class HomeViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableLiveData<Result<List<ArticlesItem?>>>()

    val articles: LiveData<Result<List<ArticlesItem?>>>
        get() = _articles

    fun findArticles(context: Context) {
        _articles.value = Result.Loading
        val errorMessageOnFailure = context.getString(R.string.text10)
        val errorMessageOnResponseFail = context.getString(R.string.text11)
        articleRepository.getArticle(errorMessageOnFailure, errorMessageOnResponseFail) { result ->
            _articles.value = result
        }
    }
}
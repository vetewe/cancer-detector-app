package com.dicoding.asclepius.view.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.ArticleRepository
import com.dicoding.asclepius.di.Injection

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val articleRepository: ArticleRepository,
    private val errorMessage: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(articleRepository) as T
        }
        throw IllegalArgumentException(errorMessage + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null

        fun getInstance(errorMessage: String): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(
                    Injection.articleRepository(),
                    errorMessage
                )
            }.also { instance = it }
    }
}
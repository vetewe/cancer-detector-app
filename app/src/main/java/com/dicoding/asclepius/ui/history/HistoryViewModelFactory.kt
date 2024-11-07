package com.dicoding.asclepius.view.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.repository.ResultRepository
import com.dicoding.asclepius.di.Injection

@Suppress("UNCHECKED_CAST")
class HistoryViewModelFactory private constructor(
    private val analyzeResultRepository: ResultRepository,
    private val errorMessage: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(analyzeResultRepository) as T
        }
        throw IllegalArgumentException(errorMessage + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null

        fun getInstance(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(
                    Injection.resultRepository(context),
                    context.getString(R.string.text13)
                )
            }.also { instance = it }
    }
}
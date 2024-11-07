package com.dicoding.asclepius.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.repository.ResultRepository
import com.dicoding.asclepius.di.Injection

@Suppress("UNCHECKED_CAST")
class ResultViewModelFactory private constructor(
    private val analyzeResultRepository: ResultRepository,
    private val errorMessageSaveSuccess: String,
    private val errorMessageAlreadyExists: String,
    private val unknownViewModelError: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(analyzeResultRepository, errorMessageSaveSuccess, errorMessageAlreadyExists) as T
        }
        throw IllegalArgumentException(unknownViewModelError + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ResultViewModelFactory? = null

        fun getInstance(context: Context): ResultViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ResultViewModelFactory(
                    Injection.resultRepository(context),
                    context.getString(R.string.text18),
                    context.getString(R.string.text19),
                    context.getString(R.string.text13)
                )
            }.also { instance = it }
    }
}
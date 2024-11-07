package com.dicoding.asclepius.view

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.ResultEntity
import com.dicoding.asclepius.data.repository.ResultRepository

class ResultViewModel(
    private val analyzeResultRepository: ResultRepository,
    private val errorMessageSaveSuccess: String,
    private val errorMessageAlreadyExists: String
) : ViewModel() {

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = _snackBarText

    fun saveDetailUser(imageAnalyze: Uri, analyzeTime: String, analyzeResult: String) {
        val imageToString = imageAnalyze.toString()

        analyzeResultRepository.getAnalyzeResultByUri(imageToString) { result ->
            if (result == null) {
                val analyzeResultEntity = ResultEntity(
                    imageUri = imageAnalyze,
                    analyzeTime = analyzeTime,
                    analyzeResult = analyzeResult
                )
                analyzeResultRepository.saveAnalyzeResult(analyzeResultEntity)
                _snackBarText.value = errorMessageSaveSuccess
            } else {
                _snackBarText.value = errorMessageAlreadyExists
            }
        }
    }

}


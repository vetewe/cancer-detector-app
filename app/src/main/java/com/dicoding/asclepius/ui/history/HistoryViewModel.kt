package com.dicoding.asclepius.view.ui.history

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.ResultEntity
import com.dicoding.asclepius.data.repository.ResultRepository

class HistoryViewModel(
    private val analyzeResultRepository: ResultRepository
) : ViewModel() {
    fun getAnalyzeResult() = analyzeResultRepository.showAnalyzeResult()

    fun removeAnalyzeResult(analyzeResultEntity: ResultEntity) {
        analyzeResultRepository.removeAnalyzeResult(analyzeResultEntity)
    }

}
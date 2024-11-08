package com.dicoding.asclepius.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.ResultEntity
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.view.ui.adapter.ResultAdapter
import com.dicoding.asclepius.view.ui.history.HistoryViewModel
import com.dicoding.asclepius.view.ui.history.HistoryViewModelFactory

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: HistoryViewModelFactory =
            HistoryViewModelFactory.getInstance(requireActivity())
        val historyViewModel: HistoryViewModel by viewModels {
            factory
        }

        val analyzeResultAdapter = ResultAdapter { analyzeResult ->
            historyViewModel.removeAnalyzeResult(analyzeResult)
        }

        historyViewModel.getAnalyzeResult().observe(viewLifecycleOwner) { result ->
            binding.progressBar2.visibility = View.GONE
            val items = arrayListOf<ResultEntity>()
            result.map {
                val item = ResultEntity(
                    id = it.id,
                    imageUri = it.imageUri,
                    analyzeResult = it.analyzeResult,
                    analyzeTime = it.analyzeTime
                )
                items.add(item)
            }
            analyzeResultAdapter.submitList(items)

            if (items.isEmpty()) {
                binding.historyText.visibility = View.VISIBLE
                binding.historyText.text = getString(R.string.text9)
            } else {
                binding.historyText.visibility = View.GONE
            }
        }

        binding.rvAnalyzeResult.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = analyzeResultAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    GridLayoutManager(requireActivity(), 2).orientation
                )
            )
            setPadding(0, 0, 0, 200)
            clipToPadding = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
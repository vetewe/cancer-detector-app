package com.dicoding.asclepius.view.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.ResultEntity
import com.dicoding.asclepius.databinding.ItemResultBinding

class ResultAdapter(
    private val onDeleteClick: (ResultEntity) -> Unit
) :
    ListAdapter<ResultEntity, ResultAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onDeleteClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val resultAnalyze = getItem(position)
        holder.bind(resultAnalyze)
    }

    class MyViewHolder(
        private val binding: ItemResultBinding,
        private val onDeleteClick: (ResultEntity) -> Unit
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        @SuppressLint("SetTextI18n")
        fun bind(result: ResultEntity) {
            binding.tvAnalyzeResult.text = result.analyzeResult
            binding.tvAnalyzeTime.text = itemView.context.getString(R.string.text20) + result.analyzeTime
            Glide.with(itemView.context)
                .load(result.imageUri)
                .into(binding.ivImageResultAnalyze)

            binding.deleteBtn.setOnClickListener {
                onDeleteClick(result)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ResultEntity> =
            object : DiffUtil.ItemCallback<ResultEntity>() {
                override fun areItemsTheSame(
                    oldItem: ResultEntity,
                    newItem: ResultEntity
                ): Boolean {
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ResultEntity,
                    newItem: ResultEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}



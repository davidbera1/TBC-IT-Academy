package com.example.learnandroid.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.CategoryItemBinding
import com.example.learnandroid.presentation.model.CategoryUi
import com.example.learnandroid.presentation.util.hide

class CategoryAdapter :
    ListAdapter<Pair<CategoryUi, Int>, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Pair<CategoryUi, Int>) {
            val (category, depth) = item
            val dots = "🔴 ".repeat(minOf(depth, 4))

            if (depth == 0) {
                binding.tvDots.hide()
            } else {
                binding.tvDots.text = dots
            }
            binding.tvName.text = category.name
        }
    }
}

class CategoryDiffUtil : DiffUtil.ItemCallback<Pair<CategoryUi, Int>>() {
    override fun areItemsTheSame(
        oldItem: Pair<CategoryUi, Int>,
        newItem: Pair<CategoryUi, Int>
    ): Boolean {
        return oldItem.first.id == newItem.first.id
    }

    override fun areContentsTheSame(
        oldItem: Pair<CategoryUi, Int>,
        newItem: Pair<CategoryUi, Int>
    ): Boolean {
        return oldItem.first == newItem.first
    }
}
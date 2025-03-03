package com.example.learnandroid.presentation.ui.recipe_details_bottom_sheet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.IngredientItemBinding
import com.example.learnandroid.presentation.model.Ingredient

class IngredientsAdapter :
    ListAdapter<Ingredient, IngredientsAdapter.IngredientsViewHolder>(IngredientsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding =
            IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.onBind(ingredient)
    }

    inner class IngredientsViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(ingredient: Ingredient) {
            binding.tvName.text = "${binding.tvName.text} ${ingredient.name}"
            binding.tvAmount.text = "${binding.tvAmount.text} ${ingredient.amount} ${ingredient.unit}"
        }
    }
}

class IngredientsDiffUtil : DiffUtil.ItemCallback<Ingredient>() {
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }
}
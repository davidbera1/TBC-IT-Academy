package com.example.learnandroid.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.RandomRecipeItemBinding
import com.example.learnandroid.presentation.model.Recipe
import com.example.learnandroid.utils.loadImage
import com.example.learnandroid.utils.setHtmlText

// add lambda callback click listener in constructor
class RandomRecipesAdapter(val onItemClicked: (Int) -> Unit) :
    ListAdapter<Recipe, RandomRecipesAdapter.RandomRecipesViewHolder>(RandomRecipesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomRecipesViewHolder {
        val binding =
            RandomRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RandomRecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RandomRecipesViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.onBind(recipe)
    }

    inner class RandomRecipesViewHolder(private val binding: RandomRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(recipe: Recipe) {
            binding.tvTitle.text = recipe.title
            val first3Sentences = recipe.summary
                ?.split(". ")
                ?.take(3)
                ?.joinToString(".\n")
                ?.plus("...")
                ?: "No summary available"
            binding.tvSummary.setHtmlText(first3Sentences)
            binding.image.loadImage(recipe.image)

            binding.root.setOnClickListener {
                onItemClicked.invoke(recipe.id)
            }
        }
    }
}

class RandomRecipesDiffUtil : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}

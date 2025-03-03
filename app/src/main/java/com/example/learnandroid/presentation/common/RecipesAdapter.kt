package com.example.learnandroid.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.RecipeItemBinding
import com.example.learnandroid.presentation.model.Recipe
import com.example.learnandroid.utils.loadImage
import com.example.learnandroid.utils.setHtmlText

class RecipesAdapter(
    val onItemClicked: (Int) -> Unit,
    val onItemLongClicked: ((Int) -> Unit)? = null
) : ListAdapter<Recipe, RecipesAdapter.RecipesViewHolder>(RecipesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val binding =
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.onBind(recipe)
    }

    inner class RecipesViewHolder(private val binding: RecipeItemBinding) :
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

            binding.clickOverlay.setOnClickListener {
                onItemClicked.invoke(recipe.id)
            }

            // for long click on favorites fragment
            binding.clickOverlay.setOnLongClickListener {
                onItemLongClicked?.invoke(recipe.id)
                onItemLongClicked != null
            }
        }
    }
}

class RecipesDiffUtil : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}

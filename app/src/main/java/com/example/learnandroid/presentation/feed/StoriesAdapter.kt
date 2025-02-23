package com.example.learnandroid.presentation.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnandroid.R
import com.example.learnandroid.databinding.StoryItemBinding
import com.example.learnandroid.presentation.model.Story

class StoriesAdapter : ListAdapter<Story, StoriesAdapter.StoriesViewHolder>(StoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val story = getItem(position)
        return holder.onBind(story)
    }

    inner class StoriesViewHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(story: Story) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(story.cover)
                    .error(R.drawable.error)
                    .into(imgStory)

                tvTitle.text = story.title
            }
        }
    }
}

class StoryDiffUtil : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }
}
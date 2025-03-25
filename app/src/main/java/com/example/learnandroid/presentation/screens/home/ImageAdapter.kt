package com.example.learnandroid.presentation.screens.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.ImageItemBinding

class ImageAdapter(
    private val onItemClick: (Uri) -> Unit
) : ListAdapter<Uri, ImageAdapter.ImageViewHolder>(UriDiffUtil()) {

    inner class ImageViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(uri: Uri) {
            binding.imgPhoto.setImageURI(uri)
            binding.root.setOnClickListener {
                onItemClick(uri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class UriDiffUtil : DiffUtil.ItemCallback<Uri>() {
    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean = oldItem == newItem
}
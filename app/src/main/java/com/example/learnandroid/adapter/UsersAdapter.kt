package com.example.learnandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.learnandroid.databinding.RecyclerItemBinding
import com.example.learnandroid.model.dto.UsersDto

class UsersAdapter :
    ListAdapter<UsersDto.Data, UsersAdapter.UsersViewHolder>(UsersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        holder.onBind(user)
    }

    inner class UsersViewHolder(private val binding: RecyclerItemBinding) :
        ViewHolder(binding.root) {
        fun onBind(data: UsersDto.Data) {
            binding.tvEmail.text = data.email
        }
    }
}

class UsersDiffUtil : DiffUtil.ItemCallback<UsersDto.Data>() {
    override fun areItemsTheSame(
        oldItem: UsersDto.Data,
        newItem: UsersDto.Data
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UsersDto.Data,
        newItem: UsersDto.Data
    ): Boolean {
        return oldItem == newItem
    }

}
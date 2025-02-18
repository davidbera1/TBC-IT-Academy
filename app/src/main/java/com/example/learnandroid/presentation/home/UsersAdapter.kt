package com.example.learnandroid.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.learnandroid.R
import com.example.learnandroid.data.local.room.entity.User
import com.example.learnandroid.databinding.RecyclerItemBinding

class UsersAdapter :
    PagingDataAdapter<User, UsersAdapter.UsersViewHolder>(UsersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = RecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.onBind(it) }
    }

    inner class UsersViewHolder(private val binding: RecyclerItemBinding) :
        ViewHolder(binding.root) {
        @SuppressLint("StringFormatMatches")
        fun onBind(data: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.avatar)
                    .into(imgAvatar)
                tvId.text = itemView.context.getString(R.string.user_id, data.id)
                tvEmail.text = data.email
                tvFullName.text = itemView.context.getString(
                    R.string.full_name,
                    data.firstName,
                    data.lastName
                )
            }
        }
    }
}

class UsersDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        return oldItem == newItem
    }
}
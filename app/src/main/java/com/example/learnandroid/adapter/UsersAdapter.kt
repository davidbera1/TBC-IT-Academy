package com.example.learnandroid.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.learnandroid.R
import com.example.learnandroid.databinding.RecyclerItemBinding
import com.example.learnandroid.model.dto.UsersDto

class UsersAdapter :
    PagingDataAdapter<UsersDto.Data, UsersAdapter.UsersViewHolder>(UsersDiffUtil()) {

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
        fun onBind(data: UsersDto.Data) {
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
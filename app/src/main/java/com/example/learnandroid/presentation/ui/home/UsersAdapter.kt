package com.example.learnandroid.presentation.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.learnandroid.R
import com.example.learnandroid.databinding.RecyclerItemBinding
import com.example.learnandroid.presentation.model.UserUi
import com.example.learnandroid.presentation.util.loadImage

class UsersAdapter :
    PagingDataAdapter<UserUi, UsersAdapter.UsersViewHolder>(UsersDiffUtil()) {

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
        fun onBind(user: UserUi) {
            with(binding) {
                imgAvatar.loadImage(user.avatar)
                tvId.text = itemView.context.getString(R.string.user_id, user.id)
                tvEmail.text = user.email
                tvFullName.text = itemView.context.getString(
                    R.string.full_name,
                    user.fullName
                )
            }
        }
    }
}

class UsersDiffUtil : DiffUtil.ItemCallback<UserUi>() {
    override fun areItemsTheSame(
        oldItem: UserUi,
        newItem: UserUi
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserUi,
        newItem: UserUi
    ): Boolean {
        return oldItem == newItem
    }
}
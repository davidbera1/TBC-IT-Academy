package com.example.learnandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.learnandroid.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnandroid.databinding.UserItemBinding
import com.example.learnandroid.room.entities.User

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(UserDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: User) {
            with(binding) {
                tvFullName.text = itemView.context.getString(
                    R.string.tv_full_name,
                    user.firstName, user.lastName
                )
                tvActivationStatus.text = getUserStatusText(user.activationStatus, itemView.context)
                tvAbout.text = user.about

                if (user.profileImage.isNullOrEmpty()) {
                    imgUser.setImageResource(R.drawable.error)
                } else {
                    Glide.with(itemView.context)
                        .load(user.profileImage)
                        .error(R.drawable.error)
                        .placeholder(R.drawable.loading)
                        .into(imgUser)
                }
            }
        }
    }

    private fun getUserStatusText(status: Int, context: Context): String {
        return when {
            status <= 0 -> context.getString(R.string.user_is_not_activated)
            status == 1 -> context.getString(R.string.online)
            status == 2 -> context.getString(R.string.active_a_few_minutes_ago)
            status in 3..22 -> context.getString(R.string.available_a_few_hours_ago)
            else -> context.getString(R.string.user_has_not_been_active_for_a_long_time)
        }
    }
}

class UserDiffUtil : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

package com.example.learnandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learnandroid.databinding.MessageLeftBinding
import com.example.learnandroid.databinding.MessageRightBinding


class MessageAdapter : ListAdapter<MessageItem, RecyclerView.ViewHolder>(MessageDiffUtil()) {

    companion object {
        private const val MESSAGE_LEFT = 0
        private const val MESSAGE_RIGHT = 1
    }

    override fun getItemViewType(position: Int): Int {
        if ((itemCount-position) % 2 == 0) {
            return MESSAGE_LEFT
        }
        else {
            return MESSAGE_RIGHT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MESSAGE_LEFT) {
            return MessageLeftViewHolder(
                MessageLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
        else {
            return MessageRightViewHolder(
                MessageRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MessageLeftViewHolder) {
            holder.onBind()
        }
        else if(holder is MessageRightViewHolder) {
            holder.onBind()
        }
    }

    inner class MessageLeftViewHolder(private var binding: MessageLeftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvMessage.text = getItem(adapterPosition).message
            binding.tvDate.text = getItem(adapterPosition).sendDate
        }
    }

    inner class MessageRightViewHolder(private var binding: MessageRightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvMessage.text = getItem(adapterPosition).message
            binding.tvDate.text = getItem(adapterPosition).sendDate
        }
    }
}

class MessageDiffUtil : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem == newItem
    }
}

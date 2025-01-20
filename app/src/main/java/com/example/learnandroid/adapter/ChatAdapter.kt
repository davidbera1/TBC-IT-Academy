package com.example.learnandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnandroid.databinding.ChatItemAttachmentBinding
import com.example.learnandroid.databinding.ChatItemTextBinding
import com.example.learnandroid.databinding.ChatItemVoiceBinding
import com.example.learnandroid.model.ChatDto

class ChatAdapter : ListAdapter<ChatDto, RecyclerView.ViewHolder>(ChatDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            CHAT_MESSAGE -> {
                return ChatTextMessageViewHolder(
                    ChatItemTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            CHAT_VOICE -> {
                return ChatVoiceMessageViewHolder(
                    ChatItemVoiceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> return ChatAttachmentViewHolder(
                ChatItemAttachmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = getItem(position)
        when (holder) {
            is ChatTextMessageViewHolder -> holder.onBind(chat)
            is ChatVoiceMessageViewHolder -> holder.onBind(chat)
            is ChatAttachmentViewHolder -> holder.onBind(chat)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val chat = getItem(position)

        return when (chat.lastMessageType) {
            "text" -> CHAT_MESSAGE
            "voice" -> CHAT_VOICE
            "attachment" -> CHAT_ATTACHMENT
            else -> CHAT_MESSAGE
        }
    }

    inner class ChatTextMessageViewHolder(private val binding: ChatItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(chat: ChatDto) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(chat.image)
                    .into(imgUser)

                tvName.text = chat.owner
                tvMessage.text = chat.lastMessage
                tvTime.text = chat.lastActive
                tvUnreadMessagesCount.text = chat.unreadMessages.toString()
            }
        }
    }

    inner class ChatVoiceMessageViewHolder(private val binding: ChatItemVoiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(chat: ChatDto) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(chat.image)
                    .into(imgUser)

                tvMessage.text = chat.lastMessage
                tvName.text = chat.owner
                tvTime.text = chat.lastActive
                tvUnreadMessagesCount.text = chat.unreadMessages.toString()
            }
        }
    }

    inner class ChatAttachmentViewHolder(private val binding: ChatItemAttachmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(chat: ChatDto) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(chat.image)
                    .into(imgUser)

                tvMessage.text = chat.lastMessage
                tvName.text = chat.owner
                tvTime.text = chat.lastActive
                tvUnreadMessagesCount.text = chat.unreadMessages.toString()
            }
        }
    }

    companion object {
        const val CHAT_MESSAGE = 0
        const val CHAT_VOICE = 1
        const val CHAT_ATTACHMENT = 2
    }
}

class ChatDiffUtil : DiffUtil.ItemCallback<ChatDto>() {
    override fun areItemsTheSame(oldItem: ChatDto, newItem: ChatDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatDto, newItem: ChatDto): Boolean {
        return oldItem == newItem
    }

}
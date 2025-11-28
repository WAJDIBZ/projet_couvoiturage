package com.example.projet_couvoiturage.ui.employee.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_couvoiturage.data.local.entity.ChatMessageEntity
import com.example.projet_couvoiturage.databinding.ItemChatBinding

class ChatAdapter(private val currentUserId: Long) :
    ListAdapter<ChatMessageEntity, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding, currentUserId)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChatViewHolder(
        private val binding: ItemChatBinding,
        private val currentUserId: Long
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessageEntity) {
            binding.tvMessage.text = message.content
            // Simple logic: align right if me, left if others (not implemented in layout for simplicity, just text)
            // In a real app, we'd have different layouts or gravity changes.
            if (message.senderId == currentUserId) {
                binding.tvSender.text = "Me"
            } else {
                binding.tvSender.text = "User ${message.senderId}"
            }
        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<ChatMessageEntity>() {
        override fun areItemsTheSame(oldItem: ChatMessageEntity, newItem: ChatMessageEntity): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ChatMessageEntity, newItem: ChatMessageEntity): Boolean = oldItem == newItem
    }
}

package com.example.datemate_sd.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.databinding.ItemContainerReceivedMessageBinding
import com.example.datemate_sd.databinding.ItemContainerSentMessageBinding
import com.example.datemate_sd.model.MessageModel
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(val senderId: String) :
    ListAdapter<MessageModel, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).senderId == senderId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val binding = ItemContainerSentMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            SentMessageViewHolder(binding)
        } else {
            val binding = ItemContainerReceivedMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ReceivedMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    class SentMessageViewHolder(private val binding: ItemContainerSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageModel) {
            binding.textSentMessage.text = message.message
            binding.textSentTime.text = formatTimestamp(message.timestamp)
        }
    }

    class ReceivedMessageViewHolder(private val binding: ItemContainerReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageModel) {
            binding.textReceivedMessage.text = message.message
            binding.textReceivedTime.text = formatTimestamp(message.timestamp)
        }
    }
}

class MessageDiffCallback : DiffUtil.ItemCallback<MessageModel>() {
    override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem.timestamp == newItem.timestamp
    }

    override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem == newItem
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

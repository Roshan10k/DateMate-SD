package com.example.datemate_sd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.R
import com.example.datemate_sd.model.MessageModel

class MessageAdapter(
    private var messages: List<MessageModel>,
    private val onItemClick: (MessageModel) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.nameDisplayTextView)
        val lastMessage: TextView = itemView.findViewById(R.id.msgDisplayTextView)
        val time: TextView = itemView.findViewById(R.id.timeDisplayTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        // Fetch user name from Firestore (replace with actual logic)
        fetchUserName(message.receiverId) { name ->
            holder.username.text = name
        }

        holder.lastMessage.text = message.message
        holder.time.text = android.text.format.DateFormat.format("hh:mm a", message.timestamp)

        holder.itemView.setOnClickListener {
            onItemClick(message)
        }
    }

    private fun fetchUserName(userId: String, callback: (String) -> Unit) {
        // Fetch user name from Firestore and call callback(name)
    }

    override fun getItemCount() = messages.size

    fun updateMessages(newMessages: List<MessageModel>) {
        messages = newMessages
        notifyDataSetChanged()
    }
}
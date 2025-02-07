package com.example.datemate_sd.ui.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.R
import com.example.datemate_sd.model.MessageModel
import com.example.datemate_sd.ui.activity.ChatPageActivity
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(private var messages: List<MessageModel>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameDisplayTextView)
        val messageTextView: TextView = view.findViewById(R.id.msgDisplayTextView)
        val timeTextView: TextView = view.findViewById(R.id.timeDisplayTextView)
        val countTextView: TextView = view.findViewById(R.id.msgCounterTextview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.nameTextView.text = message.name
        holder.messageTextView.text = message.message
        holder.timeTextView.text = formatTime(message.time)
        holder.countTextView.text = message.count.toString()

        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, ChatPageActivity::class.java).apply {
                putExtra("userName", message.name)
                putExtra("senderId", message.senderId)
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = messages.size

    private fun formatTime(timestamp: Long?): String {
        return timestamp?.let {
            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            sdf.format(Date(it))
        } ?: "Unknown"
    }

    // Function to update the list dynamically
    fun updateMessages(newMessages: List<MessageModel>) {
        messages = newMessages
        notifyDataSetChanged()
    }
}

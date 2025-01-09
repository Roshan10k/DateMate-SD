package com.example.datemate_sd.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.R

data class Message(val name: String, val message: String, val time: String, val count: Int)

class MessageAdapter(private val messages: List<Message>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

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
        holder.timeTextView.text = message.time
        holder.countTextView.text = message.count.toString()
    }

    override fun getItemCount(): Int = messages.size
}

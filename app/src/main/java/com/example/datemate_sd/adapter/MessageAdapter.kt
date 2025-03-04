package com.example.datemate_sd.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.databinding.SampleMessageBinding
import com.example.datemate_sd.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.Date
import java.util.Locale

class MessageAdapter(
    private var messages: List<MessageModel>,
    private var unreadCounts: Map<String, Int>,
    private val onMessageClick: (MessageModel) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val userNamesCache = mutableMapOf<String, String>()
    private val auth = FirebaseAuth.getInstance()

    fun updateMessages(newMessages: List<MessageModel>, newUnreadCounts: Map<String, Int>) {
        messages = newMessages
        unreadCounts = newUnreadCounts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = SampleMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        val currentUserId = auth.currentUser?.uid ?: ""

        // Determine the chat partner (other user)
        val chatPartnerId = if (message.senderId == currentUserId) message.receiverId else message.senderId
        val unreadCount = unreadCounts[chatPartnerId] ?: 0

        holder.bind(message, chatPartnerId, unreadCount)
    }

    override fun getItemCount(): Int = messages.size

    inner class MessageViewHolder(private val binding: SampleMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: MessageModel, chatPartnerId: String, unreadCount: Int) {
            // Fetch chat partner's name (if not cached)
            val userName = userNamesCache[chatPartnerId] ?: fetchUserName(chatPartnerId)

            binding.searchNameDisplay.text = userName
            binding.msgDisplayTextView.text = message.message
            binding.timeDisplayTextView.text = formatTime(message.timestamp)




            itemView.setOnClickListener { onMessageClick(message) }
        }

        private fun formatTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }

        private fun fetchUserName(userId: String): String {
            val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userName = snapshot.child("name").getValue(String::class.java)
                    if (userName != null) {
                        userNamesCache[userId] = userName // Cache the name
                        notifyDataSetChanged() // Refresh the adapter when name is fetched
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error if needed
                }
            })
            return "Loading..." // Placeholder until the name is fetched
        }
    }
}

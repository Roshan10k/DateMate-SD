package com.example.datemate_sd.repository

import android.util.Log
import com.example.datemate_sd.model.MessageModel
import com.google.firebase.database.*

class MessageRepositoryImpl : MessageRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("messages")

    override fun sendMessage(message: MessageModel, callback: (Boolean, String?) -> Unit) {
        val messageKey = database.push().key
        if (messageKey != null) {
            // Save message under senderId -> receiverId
            database.child(message.senderId).child(message.receiverId).child(messageKey).setValue(message)
                .addOnSuccessListener { callback(true, null) }
                .addOnFailureListener { exception -> callback(false, exception.message) }
        } else {
            callback(false, "Failed to generate message key")
        }
    }

    override fun getMessages(senderId: String, receiverId: String, callback: (List<MessageModel>) -> Unit) {
        // Two lists: one for messages sent from senderId -> receiverId, and one for messages from receiverId -> senderId
        val sentMessages = mutableListOf<MessageModel>()
        val receivedMessages = mutableListOf<MessageModel>()

        // Reference for messages sent by the current user to the receiver
        val refSent = database.child(senderId).child(receiverId)
        // Reference for messages sent by the receiver to the current user
        val refReceived = database.child(receiverId).child(senderId)

        // Listener for messages sent by senderId
        refSent.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sentMessages.clear()
                for (child in snapshot.children) {
                    val message = child.getValue(MessageModel::class.java)
                    message?.let { sentMessages.add(it) }
                }
                // Merge and sort whenever this listener fires
                val combined = (sentMessages + receivedMessages).distinctBy { it.timestamp }.toMutableList()
                combined.sortBy { it.timestamp }
                callback(combined)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })

        // Listener for messages received from receiverId
        refReceived.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                receivedMessages.clear()
                for (child in snapshot.children) {
                    val message = child.getValue(MessageModel::class.java)
                    message?.let { receivedMessages.add(it) }
                }
                // Merge and sort whenever this listener fires
                val combined = (sentMessages + receivedMessages).distinctBy { it.timestamp }.toMutableList()
                combined.sortBy { it.timestamp }
                callback(combined)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }
}

package com.example.datemate_sd.repository

import android.util.Log
import com.example.datemate_sd.model.MessageModel
import com.google.firebase.database.*

class MessageRepositoryImpl : MessageRepository {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("messages")

    override fun sendMessage(message: MessageModel, callback: (Boolean, String?) -> Unit) {
        val messageKey = database.push().key
        if (messageKey != null) {
            val updatedMessage = message.copy(messageId = messageKey)
            database.child(messageKey).setValue(updatedMessage)
                .addOnSuccessListener { callback(true, null) }
                .addOnFailureListener { exception -> callback(false, exception.message) }
        } else {
            callback(false, "Failed to generate message key")
        }
    }

    override fun getMessages(senderId: String, receiverId: String, callback: (List<MessageModel>) -> Unit) {
        database.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messagesList = mutableListOf<MessageModel>()
                for (child in snapshot.children) {
                    val message = child.getValue(MessageModel::class.java)
                    if (message != null &&
                        ((message.senderId == senderId && message.receiverId == receiverId) ||
                                (message.senderId == receiverId && message.receiverId == senderId))) {
                        messagesList.add(message)
                    }
                }
                messagesList.sortBy { it.timestamp }
                callback(messagesList)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    override fun markMessagesAsRead(senderId: String, receiverId: String) {
        database.orderByChild("receiverId").equalTo(receiverId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (messageSnap in snapshot.children) {
                        val message = messageSnap.getValue(MessageModel::class.java)
                        if (message != null && message.senderId == senderId && !message.isRead) {
                            messageSnap.ref.updateChildren(mapOf("isRead" to true))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MessageRepositoryImpl", "Error marking messages as read: ${error.message}")
                }
            })
    }





}

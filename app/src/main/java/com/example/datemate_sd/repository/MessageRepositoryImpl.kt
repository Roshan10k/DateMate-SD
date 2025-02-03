package com.example.datemate_sd.repository

import com.example.datemate_sd.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageRepositoryImpl: MessageRepository {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var reference = database.reference.child("mesaages")


    override fun getAllMessages(callback: (List<MessageModel>?, Boolean, String) -> Unit) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val messages = mutableListOf<MessageModel>()
                    for (messageSnapshot in snapshot.children) {
                        val message = messageSnapshot.getValue(MessageModel::class.java)
                        if (message != null) {
                            messages.add(message)
                        }
                    }
                    callback(messages, true, "Messages fetched successfully")
                } else {
                    callback(null, false, "No messages found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })

    }

        override fun addMessage(messageModel: MessageModel, callback: (Boolean, String) -> Unit) {
            val messageId = reference.push().key ?: return callback(false, "Failed to generate message ID")
            reference.child(messageId).setValue(messageModel).addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Message added successfully")
                } else {
                    callback(false, it.exception?.message ?: "Error adding message")
                }
            }
        }



    override fun deleteMessage(messageId: String, callback: (Boolean, String) -> Unit) {
        reference.child(messageId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Message deleted successfully")
            } else {
                callback(false, it.exception?.message ?: "Error deleting message")
            }
        }
    }

    override fun getMessageById(
        messageId: String,
        callback: (MessageModel?, Boolean, String) -> Unit
    ) {
        reference.child(messageId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val message = snapshot.getValue(MessageModel::class.java)
                    callback(message, true, "Message fetched successfully")
                } else {
                    callback(null, false, "Message not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

}
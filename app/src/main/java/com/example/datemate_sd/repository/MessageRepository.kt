package com.example.datemate_sd.repository

import com.example.datemate_sd.model.MessageModel

interface MessageRepository {


        fun sendMessage(message: MessageModel, callback: (Boolean, String?) -> Unit)

        fun getMessages(senderId: String, receiverId: String, callback: (List<MessageModel>) -> Unit)

        fun markMessagesAsRead(senderId: String, receiverId: String)




}
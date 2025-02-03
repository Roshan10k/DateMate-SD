package com.example.datemate_sd.repository

import com.example.datemate_sd.model.MessageModel

interface MessageRepository {

    fun getAllMessages(callback: (List<MessageModel>?, Boolean, String) -> Unit)

    fun addMessage(messageModel: MessageModel, callback: (Boolean, String) -> Unit)





    fun deleteMessage(messageId: String, callback: (Boolean, String) -> Unit)


    fun getMessageById(messageId: String, callback: (MessageModel?, Boolean, String) -> Unit)
}
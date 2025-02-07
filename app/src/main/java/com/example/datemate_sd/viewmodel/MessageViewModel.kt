package com.example.datemate_sd.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.datemate_sd.model.MessageModel
import com.example.datemate_sd.repository.MessageRepository
import com.example.datemate_sd.repository.MessageRepositoryImpl

class MessageViewModel(private val repo: MessageRepository = MessageRepositoryImpl()) : ViewModel() {

    private val _messages = MutableLiveData<List<MessageModel>>()
    val messages: LiveData<List<MessageModel>> get() = _messages

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getAllMessages() {
        repo.getAllMessages { messageList, success, message ->
            if (success) {
                _messages.value = messageList
            } else {
                _errorMessage.value = message
            }
        }
    }

    fun addMessage(messageModel: MessageModel) {
        repo.addMessage(messageModel) { success, message ->
            if (success) {
                getAllMessages() // Refresh messages after adding a new one
            } else {
                _errorMessage.value = message
            }
        }
    }

    fun deleteMessage(messageId: String) {
        repo.deleteMessage(messageId) { success, message ->
            if (success) {
                getAllMessages() // Refresh messages after deletion
            } else {
                _errorMessage.value = message
            }
        }
    }

    fun getMessageById(messageId: String, callback: (MessageModel?, Boolean, String) -> Unit) {
        repo.getMessageById(messageId, callback)
    }
}

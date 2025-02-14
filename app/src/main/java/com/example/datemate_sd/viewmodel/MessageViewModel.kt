package com.example.datemate_sd.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.datemate_sd.model.MessageModel
import com.example.datemate_sd.repository.MessageRepository

class MessageViewModel(private val chatRepository: MessageRepository) : ViewModel() {
    private val _messages = MutableLiveData<List<MessageModel>>()
    val messages: LiveData<List<MessageModel>> get() = _messages

    fun sendMessage(message: MessageModel) {
        chatRepository.sendMessage(message) { success, error ->
            if (!success) {
                // Handle error (e.g., show a toast)
            }
        }
    }

    fun loadMessages(senderId: String, receiverId: String) {
        chatRepository.getMessages(senderId, receiverId) { messages ->
            _messages.postValue(messages)
        }
    }

    fun markMessagesAsRead(senderId: String, receiverId: String) {
        chatRepository.markMessagesAsRead(senderId, receiverId)
    }




}

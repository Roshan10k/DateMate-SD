package com.example.datemate_sd.viewmodel

// NotificationViewModel.kt

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.datemate_sd.model.NotificationModel
import com.example.datemate_sd.repository.NotificationRepositoryImpl

class NotificationViewModel : ViewModel() {

    private val repository = NotificationRepositoryImpl()

    // LiveData for holding the notifications
    private val _notifications = MutableLiveData<List<NotificationModel>>()
    val notifications: LiveData<List<NotificationModel>> = _notifications

    // Fetch notifications in real-time
    fun listenForNotifications(userId: String) {
        repository.listenForNotifications(userId) { success, data, error ->
            if (success) {
                _notifications.value = data
            } else {
                Log.e("NotificationViewModel", "Error fetching notifications: $error")
            }
        }
    }

    // Save a notification to Firestore
    fun saveNotification(receiverId: String, title: String, description: String) {
        repository.saveNotification(receiverId, title, description)
    }

    // Send push notification via FCM
    fun sendPushNotification(receiverToken: String, title: String, message: String) {
        repository.sendPushNotification(receiverToken, title, message)
    }
}

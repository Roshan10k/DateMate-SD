package com.example.datemate_sd.repository

// NotificationRepositoryInterface.kt
import com.example.datemate_sd.model.NotificationModel

interface NotificationRepositoryInterface {

    fun saveNotification(receiverId: String, title: String, description: String)

    fun listenForNotifications(userId: String, callback: (Boolean, List<NotificationModel>?, String?) -> Unit)

    fun sendPushNotification(receiverToken: String, title: String, message: String)
}

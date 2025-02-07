package com.example.datemate_sd.model

data class NotificationModel(
    val title: String = "",
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

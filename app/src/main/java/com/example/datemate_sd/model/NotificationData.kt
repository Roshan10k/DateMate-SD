package com.example.datemate_sd.model

data class NotificationData(
    val token: String? = "",
    val notification: HashMap<String, String>? = null,
    val data: HashMap<String, String>? = null
)

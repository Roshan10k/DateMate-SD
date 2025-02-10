package com.example.datemate_sd.model

import java.sql.Time



data class MessageModel(
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val timestamp: Long = 0
)
package com.example.datemate_sd.model

data class MessageModel(
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val timestamp: Long = 0,
    var isRead: Boolean = false,
    var messageId: String = ""
) {
    // Add empty constructor for Firebase
    constructor() : this("", "", "", 0, false, "")
}
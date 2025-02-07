package com.example.datemate_sd.model

import java.sql.Time

data class MessageModel(
    var senderId: String ="",
    var message : String ="",
    var name: String ="",
    var count : Int =0,
    var time: Long =0L

) {
}
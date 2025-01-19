package com.example.datemate_sd.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.Adapter.NotificationAdapter
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    lateinit var binding: ActivityNotificationBinding

    var titleList =  ArrayList<String>()

    var desList = ArrayList<String>()

    lateinit var notificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerViewNotifications

        titleList.add("Kiara admani")
        titleList.add("Sabita kaphle")
        titleList.add("Hira")
        titleList.add("samir")
        titleList.add("Keshav")


        desList.add("Like your profile")
        desList.add("Send message")
        desList.add("Like your profile")
        desList.add("send message")
        desList.add("Like your profile")

        notificationAdapter = NotificationAdapter(
            this@NotificationActivity,
            titleList, desList
        )

        recyclerView.adapter = notificationAdapter

        recyclerView.layoutManager = LinearLayoutManager(
            this@NotificationActivity
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
package com.example.datemate_sd.ui.activity

// NotificationActivity.kt
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityNotificationBinding
import com.example.datemate_sd.ViewModel.NotificationViewModel
import com.example.datemate_sd.adapter.NotificationAdapter

class NotificationActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var binding: ActivityNotificationBinding

    lateinit var notificationAdapter: NotificationAdapter

    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerViewNotifications

        notificationAdapter = NotificationAdapter(this)
        recyclerView.adapter = notificationAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observer for notifications LiveData
        notificationViewModel.notifications.observe(this, Observer { notifications ->
            notificationAdapter.updateNotifications(notifications)
        })

        // Fetch notifications for the current user (example: user ID "user_id_here")
        notificationViewModel.listenForNotifications("user_id_here")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

package com.example.datemate_sd.ui.activity

// NotificationActivity.kt
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityNotificationBinding
import com.example.datemate_sd.viewmodel.NotificationViewModel
import com.example.datemate_sd.Adapter.NotificationAdapter

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter

    private val notificationViewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize adapter with an empty list
        notificationAdapter = NotificationAdapter(this, emptyList())

        binding.recyclerViewNotifications.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(this@NotificationActivity)
        }

        // Observe notifications LiveData
        notificationViewModel.notifications.observe(this, Observer { notifications ->
            notificationAdapter.updateNotifications(notifications)
        })

        // Fetch notifications for the current user (Replace with actual user ID)
        notificationViewModel.listenForNotifications("user_id_here")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

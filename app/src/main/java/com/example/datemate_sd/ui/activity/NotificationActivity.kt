package com.example.datemate_sd.ui.activity

// NotificationActivity.kt
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityNotificationBinding
import com.example.datemate_sd.adapter.NotificationAdapter
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.ui.fragment.DashboardFragment
import com.example.datemate_sd.viewmodel.UserViewModel

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationAdapter: NotificationAdapter


    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        // Initialize adapter with an empty list
        notificationAdapter = NotificationAdapter(this, emptyList(),userViewModel)

        binding.recyclerViewNotifications.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(this@NotificationActivity)
        }

        // Observe notifications LiveData
        userViewModel.notifications.observe(this, Observer { notifications ->
            val reversedNotifications = notifications.reversed()
            notificationAdapter.updateNotifications(reversedNotifications)
        })

        val userId = userViewModel.getCurrentUser()?.uid.toString()
        userViewModel.getNotificationsForUser(userId)

        binding.backButton.setOnClickListener{
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.R
import com.example.datemate_sd.adapter.ChatAdapter
import com.example.datemate_sd.databinding.ActivityChatPageBinding
import com.example.datemate_sd.model.MessageModel
import com.example.datemate_sd.repository.MessageRepositoryImpl
import com.example.datemate_sd.ui.fragment.MessageFragment
import com.example.datemate_sd.viewmodel.MessageViewModel
import com.example.datemate_sd.viewmodel.MessageViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class ChatPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatPageBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<MessageModel>()

    private val auth = FirebaseAuth.getInstance()
    private var receiverId: String? = null
    private var senderId: String? = null
    private var userName: String? = null
    private var userImage: String? = null


    private val viewModel: MessageViewModel by viewModels {
        MessageViewModelFactory(MessageRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from intent
        receiverId = intent.getStringExtra("receiverId")
        senderId = auth.currentUser?.uid
        userName = intent.getStringExtra("userName")
        userImage = intent.getStringExtra("userImage")

        if (receiverId.isNullOrEmpty() || senderId.isNullOrEmpty()) {
            Log.e("ChatPageActivity", "Invalid sender or receiver ID")
            finish()
            return
        }

        // Set default values if userName or userImage is null
        if (userName.isNullOrEmpty()) {
            userName = "User"
        }

        // Load user profile image safely
        if (!userImage.isNullOrEmpty()) {
            Picasso.get()
                .load(userImage)
                .placeholder(R.drawable.sampleperson1) // Add a placeholder image
                .into(binding.imageView)
        }

        // Set the username in the UI
        binding.textName.text = userName

        // Use ViewModel to mark messages as read
        viewModel.markMessagesAsRead(senderId!!, receiverId!!)




        setupRecyclerView()
        observeMessages()

        // Send message on click
        binding.layoutSend.setOnClickListener { sendMessage() }

        // Load chat messages
        viewModel.loadMessages(senderId!!, receiverId!!)

        viewModel.markMessagesAsRead(senderId!!, receiverId!!)

        binding.backArrow.setOnClickListener {
            finish()
        }


    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(senderId!!)
        binding.chatRecycler.apply {
            layoutManager = LinearLayoutManager(this@ChatPageActivity).apply {
                stackFromEnd = true
            }
            adapter = chatAdapter
        }
    }

    private fun observeMessages() {
        viewModel.messages.observe(this) { newMessages ->
            Log.d("ChatPageActivity", "New messages received: $newMessages")

            // Update RecyclerView
            messages.clear()
            messages.addAll(newMessages)
            chatAdapter.submitList(ArrayList(messages)) // Ensure immutability

            // Scroll to latest message
            binding.chatRecycler.post {
                binding.chatRecycler.scrollToPosition(messages.size - 1)
            }
        }
    }

    private fun sendMessage() {
        val messageText = binding.inputMessage.text.toString().trim()
        if (messageText.isEmpty()) return

        val message = MessageModel(
            senderId = senderId!!,
            receiverId = receiverId!!,
            message = messageText,
            timestamp = System.currentTimeMillis(),
            isRead = false
        )

        viewModel.sendMessage(message)
        binding.inputMessage.text.clear()
    }

}




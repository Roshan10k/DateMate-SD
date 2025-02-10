package com.example.datemate_sd.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.adapter.ChatAdapter
import com.example.datemate_sd.databinding.ActivityChatPageBinding
import com.example.datemate_sd.model.MessageModel
import com.example.datemate_sd.repository.MessageRepositoryImpl
import com.example.datemate_sd.viewmodel.MessageViewModel
import com.example.datemate_sd.viewmodel.MessageViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class ChatPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatPageBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<MessageModel>()

    private val auth = FirebaseAuth.getInstance()
    private var receiverId: String? = null
    private var senderId: String? = null
    private var userName: String? = null // Variable to hold the username
    private var userImage: String?=null

    private val viewModel: MessageViewModel by viewModels {
        MessageViewModelFactory(MessageRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiverId = intent.getStringExtra("receiverId")
        senderId = auth.currentUser?.uid
        userName = intent.getStringExtra("userName") // Get the username passed from ProfilePageActivity
        userImage= intent.getStringExtra("userImage")
        Picasso.get().load(userImage).into(binding.imageView)

        // Set the username in a TextView (assuming you have a TextView with id textUsername)
        binding.textName.text = userName

        if (receiverId.isNullOrEmpty() || senderId.isNullOrEmpty()) {
            Log.e("ChatPageActivity", "Invalid sender or receiver ID")
            finish()
            return
        }

        setupRecyclerView()
        observeMessages()

        binding.layoutSend.setOnClickListener { sendMessage() }

        // Load previous messages
        viewModel.loadMessages(senderId!!, receiverId!!)
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
            messages.clear()
            messages.addAll(newMessages)
            chatAdapter.submitList(messages)

            // Auto-scroll to the latest message
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
            timestamp = System.currentTimeMillis()
        )

        viewModel.sendMessage(message)
        binding.inputMessage.text.clear()
    }
}

package com.example.datemate_sd.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.Adapter.ChatAdapter
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityChatPageBinding
import com.example.datemate_sd.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatPageBinding
    lateinit var adapter: ChatAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val messageCollection = firestore.collection("chats") // Firestore collection for messages
    private val messageList = ArrayList<MessageModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("senderId")

        supportActionBar?.title = name

        // Set up RecyclerView
        adapter = ChatAdapter(this, messageList)
        binding.chatRecycler.layoutManager = LinearLayoutManager(this)
        binding.chatRecycler.adapter = adapter

        // Load previous messages (if any) from Firestore
        loadMessages()

        // Send message when send button is clicked
        binding.layoutSend.setOnClickListener {
            val messageText = binding.inputMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val senderId = FirebaseAuth.getInstance().currentUser?.uid
                sendMessage(messageText, senderId)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to send the message to Firestore
    private fun sendMessage(messageText: String, senderId: String?) {
        val message = MessageModel(
            senderId = senderId ?: "",
            message = messageText,
            name = "User",  // You can customize the name or fetch it dynamically
            count = 0,
            time = System.currentTimeMillis()
        )

        // Add message to Firestore
        messageCollection.add(message)
            .addOnSuccessListener {
                binding.inputMessage.text.clear() // Clear input after sending
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()

                // Optionally update RecyclerView with new message
                messageList.add(message)
                adapter.notifyItemInserted(messageList.size - 1)
                binding.chatRecycler.scrollToPosition(messageList.size - 1)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to send message: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Function to load previous messages from Firestore
    private fun loadMessages() {
        messageCollection.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val message = document.toObject(MessageModel::class.java)
                    messageList.add(message)
                }
                adapter.notifyDataSetChanged()
                if (messageList.isNotEmpty()) {
                    binding.chatRecycler.scrollToPosition(messageList.size - 1)  // Scroll to the latest message
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load messages: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

package com.example.datemate_sd.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.adapter.MessageAdapter
import com.example.datemate_sd.databinding.FragmentMessageBinding
import com.example.datemate_sd.model.MessageModel
import com.example.datemate_sd.repository.MessageRepositoryImpl
import com.example.datemate_sd.ui.activity.ChatPageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<MessageModel>()
    private lateinit var auth: FirebaseAuth
    private val messageRepository = MessageRepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeFirebase()
        setupRecyclerView()
        fetchMessagesFromDatabase()
    }

    private fun initializeFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(messages, emptyMap()) { selectedMessage ->
            navigateToChatPage(selectedMessage)
        }

        binding.messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigateToChatPage(selectedMessage: MessageModel) {
        val currentUserId = auth.currentUser?.uid
        val chatPartnerId = if (selectedMessage.senderId == currentUserId)
            selectedMessage.receiverId else selectedMessage.senderId

        FirebaseDatabase.getInstance().reference.child("users")
            .child(chatPartnerId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userName = snapshot.child("name").getValue(String::class.java)
                    val userImage = snapshot.child("imageurl").getValue(String::class.java)


                    val intent = Intent(requireContext(), ChatPageActivity::class.java).apply {
                        putExtra("receiverId", chatPartnerId)
                        putExtra("userName", userName)
                        putExtra("userImage", userImage)
                    }
                    startActivity(intent)

                    // Mark messages as read
                    messageRepository.markMessagesAsRead(currentUserId ?: "", chatPartnerId)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Error fetching user details: ${error.message}")
                    // Start chat activity with default values if user fetch fails
                    startChatActivityWithDefaults(chatPartnerId)
                }
            })
    }

    private fun startChatActivityWithDefaults(chatPartnerId: String) {
        val intent = Intent(requireContext(), ChatPageActivity::class.java).apply {
            putExtra("receiverId", chatPartnerId)
            putExtra("userName", "User")
            putExtra("userImage", "")
        }
        startActivity(intent)
    }

    private fun fetchMessagesFromDatabase() {
        val currentUserId = auth.currentUser?.uid ?: return

        FirebaseDatabase.getInstance().reference.child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatMap = mutableMapOf<String, MessageModel>()
                    val unreadCountMap = mutableMapOf<String, Int>()

                    for (messageSnapshot in snapshot.children) {
                        processMessageSnapshot(
                            messageSnapshot,
                            currentUserId,
                            chatMap,
                            unreadCountMap
                        )
                    }

                    updateMessages(chatMap, unreadCountMap)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Error fetching messages: ${error.message}")
                }
            })
    }

    private fun processMessageSnapshot(
        messageSnapshot: DataSnapshot,
        currentUserId: String,
        chatMap: MutableMap<String, MessageModel>,
        unreadCountMap: MutableMap<String, Int>
    ) {
        val message = messageSnapshot.getValue(MessageModel::class.java)
        if (message != null && (message.senderId == currentUserId || message.receiverId == currentUserId)) {
            message.messageId = messageSnapshot.key ?: ""

            val chatPartnerId = if (message.senderId == currentUserId)
                message.receiverId else message.senderId

            // Update last message for this chat
            if (!chatMap.containsKey(chatPartnerId) ||
                message.timestamp > chatMap[chatPartnerId]!!.timestamp) {
                chatMap[chatPartnerId] = message
            }

            // Update unread count
            if (!message.isRead && message.receiverId == currentUserId) {
                unreadCountMap[chatPartnerId] = (unreadCountMap[chatPartnerId] ?: 0) + 1
            }
        }
    }

    private fun updateMessages(
        chatMap: Map<String, MessageModel>,
        unreadCountMap: Map<String, Int>
    ) {
        messages.clear()
        messages.addAll(chatMap.values.sortedByDescending { it.timestamp })
        messageAdapter.updateMessages(messages, unreadCountMap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "MessageFragment"
    }
}
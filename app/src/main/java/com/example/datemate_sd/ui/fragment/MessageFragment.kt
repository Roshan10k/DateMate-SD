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

import com.example.datemate_sd.ui.activity.ChatPageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<MessageModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        messageAdapter = MessageAdapter(messages) { selectedMessage ->
            val intent = Intent(requireContext(), ChatPageActivity::class.java).apply {
                putExtra("receiverId", selectedMessage.receiverId)
            }
            startActivity(intent)
        }
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messageRecyclerView.adapter = messageAdapter

        // Fetch messages
        fetchMessagesFromDatabase()
    }

    private fun fetchMessagesFromDatabase() {
        val db = FirebaseFirestore.getInstance()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("messages")
            .whereIn("senderId", listOf(currentUserId))
            .whereIn("receiverId", listOf(currentUserId))
            .get()
            .addOnSuccessListener { documents ->
                val fetchedMessages = documents.toObjects(MessageModel::class.java)
                messages.clear()
                messages.addAll(fetchedMessages)
                messageAdapter.updateMessages(messages)
            }
            .addOnFailureListener { e ->
                Log.e("com.example.datemate_sd.ui.fragment.MessageFragment", "Error fetching messages", e)
            }
    }
}
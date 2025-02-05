package com.example.datemate_sd.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.databinding.FragmentMessageBinding

import com.example.datemate_sd.ui.Adapter.MessageAdapter
import com.example.datemate_sd.viewmodel.MessageViewModel

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageViewModel: MessageViewModel  // Make sure to initialize this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ViewModel
        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

        // Set up RecyclerView
        messageAdapter = MessageAdapter(emptyList())
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messageRecyclerView.adapter = messageAdapter

        // Observe ViewModel data
        observeMessages()

        // Fetch messages from Firebase
        messageViewModel.getAllMessages()
    }

    private fun observeMessages() {
        messageViewModel.messages.observe(viewLifecycleOwner) { messages ->
            messageAdapter.updateMessages(messages)
        }

        messageViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

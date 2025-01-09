package com.example.datemate_sd.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datemate_sd.databinding.FragmentMessageBinding
import com.example.datemate_sd.ui.Adapter.Message
import com.example.datemate_sd.ui.Adapter.MessageAdapter

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize binding
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sample data
        val messages = listOf(
            Message("Person1", "Hello, how are you doing", "2 min ago", 1),
            Message("Person2", "Can we talk later?", "5 min ago", 2),
            Message("Person3", "Meeting at 5 PM", "10 min ago", 1)
        )

        // Set up RecyclerView
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messageRecyclerView.adapter = MessageAdapter(messages)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

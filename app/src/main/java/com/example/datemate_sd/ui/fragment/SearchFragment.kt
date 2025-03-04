package com.example.datemate_sd.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.datemate_sd.R
import com.example.datemate_sd.adapter.UserAdapter
import com.example.datemate_sd.databinding.FragmentSearchBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepositoryImpl

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private lateinit var userAdapter: UserAdapter
    private var userList = mutableListOf<UserModel>()
    private val userRepository = UserRepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)

        // Initialize RecyclerView
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter(userList)
        binding.searchRecyclerView.adapter = userAdapter

        // Set up SearchView listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterUsers(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    fetchUsers { filterUsers(newText) }  // Fetch users only when searching
                } else {
                    clearResults() // Clears the list when search is empty
                }
                return true
            }
        })

        return view
    }

    // Fetch users only when a search query is entered
    private fun fetchUsers(callback: () -> Unit) {
        userRepository.getAllUsers { users, success, message ->
            if (success && users != null) {
                userList.clear()
                userList.addAll(users)
                callback()  // Execute filtering after fetching users
            }
        }
    }

    private fun filterUsers(query: String) {
        val filteredList = userList.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.username.contains(query, ignoreCase = true)
        }
        userAdapter.updateList(filteredList)
    }

    private fun clearResults() {
        userAdapter.updateList(emptyList())  // Clear the RecyclerView when no query
    }
}

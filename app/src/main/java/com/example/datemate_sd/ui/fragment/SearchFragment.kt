package com.example.datemate_sd.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.datemate_sd.R
import com.example.datemate_sd.adapter.UserAdapter
import com.example.datemate_sd.ui.activity.BottomSheetLayout
import com.example.datemate_sd.databinding.FragmentSearchBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.google.android.material.bottomsheet.BottomSheetDialog

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

//        val filterIcon: ImageView = view.findViewById(R.id.filterIcon)



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

    private fun showBottomSheet(filterIcon: ImageView) {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        // Handle button click in the bottom sheet
        bottomSheetDialog.findViewById<View>(R.id.applyButton)?.setOnClickListener {
            // Reset filterIcon background color to default
            filterIcon.setBackgroundResource(R.drawable.button_bg) // Default background

            // Dismiss the bottom sheet when apply is clicked
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show() // Show the bottom sheet dialog
    }
}


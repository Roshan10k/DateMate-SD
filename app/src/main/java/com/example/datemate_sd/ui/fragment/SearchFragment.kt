package com.example.datemate_sd.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.datemate_sd.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.fragment.app.Fragment


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the filterIcon ImageView
        val filterIcon: ImageView = view.findViewById(R.id.filterIcon)

        // Set click listener for filterIcon
        filterIcon.setOnClickListener {
            // Change the background color of the filterIcon to gradient
            filterIcon.setBackgroundResource(R.drawable.gradient)

            // Use Handler to revert the background color back after 2 seconds (2000 milliseconds)
            Handler().postDelayed({
                filterIcon.setBackgroundResource(R.drawable.button_bg) // Reset to default background
            }, 2000)  // 2000 milliseconds = 2 seconds

            // Show the bottom sheet after clicking the filter icon
            showBottomSheet(filterIcon)
        }
    }

    // Function to display the bottom sheet
    private fun showBottomSheet(filterIcon: ImageView) {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        // Handle button click in the bottom sheet
        bottomSheetView.findViewById<View>(R.id.applyButton).setOnClickListener {
            // Reset filterIcon background color to default
            filterIcon.setBackgroundResource(R.drawable.button_bg) // Default background

            // Dismiss the bottom sheet when apply is clicked
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show() // Show the bottom sheet dialog
    }
}

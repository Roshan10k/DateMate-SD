package com.example.datemate_sd

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfilePageActivity : AppCompatActivity() {

    private lateinit var aboutTextView: TextView
    private lateinit var readMoreTextView: TextView
    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.image1)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        aboutTextView = findViewById(R.id.about_text)
        readMoreTextView = findViewById(R.id.read_more)

        // Check if "Read More" should be shown based on text line count
        aboutTextView.viewTreeObserver.addOnGlobalLayoutListener {
            if (aboutTextView.lineCount <= 3) {
                // Hide "Read More" if text fits within 2 lines
                readMoreTextView.visibility = TextView.GONE
            } else {
                // Show "Read More" if text exceeds 2 lines
                readMoreTextView.visibility = TextView.VISIBLE
            }
        }

        // Handle "Read More" click event
        readMoreTextView.setOnClickListener {
            toggleTextExpansion()
        }
    }

    private fun toggleTextExpansion() {
        if (isExpanded) {
            // Collapse text to show only 2 lines
            aboutTextView.maxLines = 4
            aboutTextView.ellipsize = TextUtils.TruncateAt.END
            readMoreTextView.text = getString(R.string.read_more) // "Read More"
        } else {
            // Expand text to show all lines
            aboutTextView.maxLines = Int.MAX_VALUE
            aboutTextView.ellipsize = null
            readMoreTextView.text = getString(R.string.read_less) // "Read Less"
        }
        isExpanded = !isExpanded
    }
}

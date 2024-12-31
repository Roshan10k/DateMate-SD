package com.example.datemate_sd.ui.activity

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityProfileDetailsBinding

class ProfileDetailsActivity : AppCompatActivity() {

    lateinit var binding:ActivityProfileDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dateInput.isClickable=true
        binding.dateInput.isFocusable=false
        binding.dateInput.setOnClickListener {
            loadCalender()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    private fun loadCalender() {
        val c= Calendar.getInstance()
        val year= c.get(Calendar.YEAR)
        val month= c.get(Calendar.MONTH)
        val day= c.get(Calendar.DAY_OF_MONTH)

        val dialog= DatePickerDialog(
            this@ProfileDetailsActivity,
            DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                binding.dateInput.setText("$year/${month+1}/$day")
            },year,month,day
        )

        dialog.show()
    }


}

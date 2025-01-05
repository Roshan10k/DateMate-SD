package com.example.datemate_sd.ui.activity

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityProfileDetailsBinding
import android.widget.Toast

class ProfileDetailsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding:ActivityProfileDetailsBinding

    var cities= arrayOf("Kathmandu","Bhaktapur","Lalitpur","Pokhara","Butwal","Janakpur","Dharan","Mahendranagar","Biratnagar")

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

        val cityAdapter = ArrayAdapter(
            this@ProfileDetailsActivity,
            android.R.layout.simple_dropdown_item_1line,
            cities
        )
        binding.addressSpinner.adapter = cityAdapter
        binding.addressSpinner.onItemSelectedListener = this

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
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

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        val selectedCity = cities[position]
        // Perform any action with the selected city here if needed
        Toast.makeText(this, "Selected city: $selectedCity", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Optional: Handle the case when no item is selected
        Toast.makeText(this, "No city selected", Toast.LENGTH_SHORT).show()
    }



}

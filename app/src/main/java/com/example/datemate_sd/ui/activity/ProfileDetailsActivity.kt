package com.example.datemate_sd.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
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

        binding.backBtn.setOnClickListener{
            val intent = Intent(this@ProfileDetailsActivity, SignupActivity::class.java)
            startActivity(intent)
        }




        binding.continueBtn.setOnClickListener {
                    val name = binding.nameInput.text.toString().trim()
                    val email = binding.emailInput.text.toString().trim()
                    val phone = binding.phoneInput.text.toString().trim()
                    val dob = binding.dateInput.text.toString().trim()

                    val emailPattern = "[a-zA-Z0-9._%+-]+@gmail\\.com" // Regex for valid Gmail address
                    val phonePattern = "\\d{10}" // Regex for exactly 10 digits



                    when {
                        name.isEmpty() -> {
                            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                        }
                        email.isEmpty() -> {
                            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                        }
                        !email.matches(emailPattern.toRegex()) -> {
                            Toast.makeText(this, "Email must be a valid Gmail address", Toast.LENGTH_SHORT).show()
                        }
                        phone.isEmpty() -> {
                            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                        }
                        !phone.matches(phonePattern.toRegex()) -> {
                            Toast.makeText(this, "Phone number must be exactly 10 digits", Toast.LENGTH_SHORT).show()
                        }
                        dob.isEmpty() -> {
                            Toast.makeText(this, "Please select your date of birth", Toast.LENGTH_SHORT).show()

                        }
                        else -> {
                            // All validations passed, proceed to the next activity
                            val intent = Intent(this@ProfileDetailsActivity, GenderActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }






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

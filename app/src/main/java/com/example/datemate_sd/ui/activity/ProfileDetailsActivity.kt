package com.example.datemate_sd.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datemate_sd.databinding.ActivityProfileDetailsBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.utils.ImageUtils
import com.example.datemate_sd.viewmodel.UserViewModel
import com.squareup.picasso.Picasso
import java.util.*

class ProfileDetailsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityProfileDetailsBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userModel: UserModel // Store the UserModel instance
    private var userId: String? = null

    lateinit var imageUtils: ImageUtils

    var imageUri: Uri? = null

    private val cities = arrayOf(
        "Kathmandu", "Bhaktapur", "Lalitpur", "Pokhara",
        "Butwal", "Janakpur", "Dharan", "Mahendranagar", "Biratnagar"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtils(this)
        imageUtils.registerActivity { url ->
            url.let { it ->
                imageUri = it
                Picasso.get().load(it).into(binding.profileImg)
            }
        }

        binding.ImageBtn.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        // Initialize the UserRepository and ViewModel
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        // Retrieve the UserModel from the intent
        userModel = intent.getParcelableExtra("User_Model") ?: UserModel()
        userId = userModel.UserId // Get the userId from the UserModel

        // Set up the city spinner
        val cityAdapter = ArrayAdapter(
            this@ProfileDetailsActivity,
            android.R.layout.simple_dropdown_item_1line,
            cities
        )
        binding.AddressSpinner.adapter = cityAdapter
        binding.AddressSpinner.onItemSelectedListener = this



        // Set up the date input click listener
        binding.DateInput.setOnClickListener { loadCalendar() }

        // Set up the continue button click listener
        binding.continueBtnPD.setOnClickListener {
            uploadImage()
        }
    }

    private fun loadCalendar() {
        val c = Calendar.getInstance()
        val dialog = DatePickerDialog(this, { _, year, month, day ->
            binding.DateInput.setText("$year/${month + 1}/$day")
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedCity = cities[position]
        Toast.makeText(this, "Selected city: $selectedCity", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "No city selected", Toast.LENGTH_SHORT).show()
    }

    private fun uploadImage() {
//        loadingUtils.show()
        imageUri?.let { uri ->
            userViewModel.uploadImage(this, uri) { imageUrl ->
                Log.d("checpoirs", imageUrl.toString())
                if (imageUrl != null) {
                    adduser(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        }
    }

    private fun adduser(url: String) {
//        loadingUtils.show()
        val name = binding.NameInput.text.toString().trim()
        val username = binding.UsernameInput.text.toString().trim()
        val phone = binding.PhoneInput.text.toString().trim()
        val dob = binding.DateInput.text.toString().trim()
        val address = binding.AddressSpinner.selectedItem.toString()
        val imageurl = url.toString()
        // Validate input fields
        if (name.isEmpty() || username.isEmpty() || phone.isEmpty() || dob.isEmpty() || address.isEmpty()|| imageurl.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
        // Update the UserModel with the collected data
        userModel.name = name
        userModel.username = username
        userModel.phnNumber = phone
        userModel.dateOfBirth = dob
        userModel.address = address
        userModel.imageurl = imageurl

        val intent = Intent(this, GenderActivity::class.java)
        intent.putExtra("User_Model", userModel) // Pass the UserModel to the next activity
        startActivity(intent)

    }
}

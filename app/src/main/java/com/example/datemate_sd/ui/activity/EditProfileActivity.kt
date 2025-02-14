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

class EditProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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

        binding.editBtn.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        binding.continueBtnPD.setOnClickListener {
            uploadImage()
        }


        // Initialize the UserRepository and ViewModel
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        // Retrieve the UserModel from the intent
        userModel = intent.getParcelableExtra("User_Model") ?: UserModel()
        userId = userModel.UserId // Get the userId from the UserModel

        // Set up the city spinner
        val cityAdapter = ArrayAdapter(
            this@EditProfileActivity,
            android.R.layout.simple_dropdown_item_1line,
            cities
        )
        binding.addressSpinner.adapter = cityAdapter
        binding.addressSpinner.onItemSelectedListener = this

        // Set up the back button to navigate to the signup screen
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        // Set up the date input click listener
        binding.dateInput.setOnClickListener { loadCalendar() }


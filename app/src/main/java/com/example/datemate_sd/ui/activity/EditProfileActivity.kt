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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityEditProfileBinding
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.ui.activity.ProfileDetailsActivity
import com.example.datemate_sd.utils.ImageUtils
import com.example.datemate_sd.viewmodel.UserViewModel
import com.squareup.picasso.Picasso
import java.util.Calendar

class EditProfileActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    lateinit var userViewModel: UserViewModel
    lateinit var binding: ActivityEditProfileBinding
    lateinit var imageUtils: ImageUtils
    private var currentImageUrl: String? = null
    var imageUri: Uri? = null
    private val cities = arrayOf(
        "Kathmandu", "Bhaktapur", "Lalitpur", "Pokhara",
        "Butwal", "Janakpur", "Dharan", "Mahendranagar", "Biratnagar"
    )

    var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        userId = userViewModel.getCurrentUser()?.uid ?: ""

        userViewModel.getUserFromDatabase(userId)
        userViewModel.users.observe(this){
            Picasso.get()
                .load(it?.imageurl)
                .into(binding.editprofileImg)

            currentImageUrl = it?.imageurl
            binding.editNameInput.setText(it?.name.toString())
            binding.editUsernameInput.setText(it?.username.toString())
            binding.editPhoneInput.setText(it?.phnNumber.toString())
            binding.editDateInput.setText(it?.dateOfBirth.toString())
            it?.address?.let { address ->
                val adapter = binding.editAddressSpinner.adapter as? ArrayAdapter<String>
                adapter?.let {
                    val position = it.getPosition(address)
                    if (position >= 0) {
                        binding.editAddressSpinner.setSelection(position)
                    }
                }
            }
        }

        imageUtils = ImageUtils(this)
        imageUtils.registerActivity { url ->
            url.let { it ->
                imageUri = it
                Picasso.get().load(it).into(binding.editprofileImg)
            }
        }

        binding.editImageBtn.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        // Set up the city spinner
        val cityAdapter = ArrayAdapter(
            this@EditProfileActivity,
            android.R.layout.simple_dropdown_item_1line,
            cities
        )

        binding.editAddressSpinner.adapter = cityAdapter
        binding.editAddressSpinner.onItemSelectedListener = this


        binding.editbackBtn.setOnClickListener {
            finish()
        }

        binding.editDateInput.setOnClickListener { loadCalendar() }


        binding.updateBtnPD.setOnClickListener{
            Log.d("foredit","I am here ")
            uploadImage()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun loadCalendar() {
        val c = Calendar.getInstance()
        val dialog = DatePickerDialog(this, { _, year, month, day ->
            binding.editDateInput.setText("$year/${month + 1}/$day")
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
        // If imageUri is selected (new image), upload it
        imageUri?.let { uri ->
            userViewModel.uploadImage(this, uri) { imageUrl ->
                Log.d("checpoirs", imageUrl.toString())
                if (imageUrl != null) {
                    Log.d("image url", "here it is $imageUrl")
                    updateUser(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        } ?: run {
            // If no new image is selected, use the current image URL
            currentImageUrl?.let { imageUrl ->
                updateUser(imageUrl)
            } ?: run {
                Log.e("Update Error", "No image URL available")
            }
        }
    }

    private fun updateUser(url: String) {
        Log.d("UpdateProfile1", "I am here")
        val name = binding.editNameInput.text.toString()
        val username = binding.editUsernameInput.text.toString().trim()
        val phone = binding.editPhoneInput.text.toString().trim()
        val dob = binding.editDateInput.text.toString().trim()
        val address = binding.editAddressSpinner.selectedItem.toString()
        val imageUrl = url.toString()

        var updatedData = mutableMapOf<String,Any>()

        updatedData["name"] = name
        updatedData["username"] = username
        updatedData["phnNumber"] = phone
        updatedData["dateOfBirth"] = dob
        updatedData["address"] = address
        updatedData["imageurl"] = imageUrl
        Log.d("UpdateProfile2", "data: $updatedData")
        userViewModel.updateProfile(userId,updatedData){
            success,message->
            Log.d("UpdateProfile3", "Success: $success, Message: $message")
            if (success){
                Toast.makeText(this@EditProfileActivity,message, Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this@EditProfileActivity,message, Toast.LENGTH_LONG).show()
            }
        }


    }
}
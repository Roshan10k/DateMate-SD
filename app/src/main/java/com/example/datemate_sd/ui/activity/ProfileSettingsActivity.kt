package com.example.datemate_sd.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityProfileSettingsBinding

class ProfileSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSettingsBinding
    private val IMAGE_PICK_REQUEST_CODE = 100

    private val imageUris = mutableListOf<Uri?>()
    private lateinit var imageViewList: List<ImageView>
    private val maxImages = 6
    private val minImages = 2
    private val minNameLength = 5
    private val minInterests = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageViewList = listOf(
            binding.profileImage,
            binding.image1,
            binding.image2,
            binding.image3,
            binding.image4,
            binding.image5,
            binding.image6
        )

        setupImageViews()
        loadImages()

        binding.changeImageButton.setOnClickListener {
            openGalleryForProfileImage()
        }

        binding.saveProfileButton.setOnClickListener {
            if (validateInputs()) {
                saveProfileToDatabase()
            }
        }
    }

    private fun setupImageViews() {
        imageViewList.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                openGallery(index)
            }

            imageView.setOnLongClickListener {
                removeImage(index)
                true
            }
        }
    }

    private fun removeImage(index: Int) {
        imageUris[index] = null
        imageViewList[index].setBackgroundResource(R.drawable.button_bg)
        imageViewList[index].setImageDrawable(null)
    }

    private fun loadImages() {
        val savedImages = fetchImagesFromDatabase()
        savedImages.forEachIndexed { index, uri ->
            if (index < imageViewList.size) {
                imageUris.add(uri)
                imageViewList[index].setImageURI(uri)
            }
        }
        while (imageUris.size < imageViewList.size) {
            imageUris.add(null)
        }
    }

    private fun openGallery(index: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE + index)
    }

    private fun openGalleryForProfileImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val index = requestCode - IMAGE_PICK_REQUEST_CODE

            if (index in 0 until imageViewList.size && selectedImageUri != null) {
                imageUris[index] = selectedImageUri
                imageViewList[index].setImageURI(selectedImageUri)
            } else if (requestCode == IMAGE_PICK_REQUEST_CODE && selectedImageUri != null) {
                binding.profileImage.setImageURI(selectedImageUri)
            } else {
                Toast.makeText(this, "Image selection failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val name = binding.nameEdit.text.toString().trim()
        val username = binding.usernameEdit.text.toString().trim()
        val bio = binding.aboutEdit.text.toString().trim()

        val selectedInterests = getSelectedInterests()

        return when {
            name.length < minNameLength -> {
                Toast.makeText(this, "Name must be at least $minNameLength characters long.", Toast.LENGTH_SHORT).show()
                false
            }
            username.isEmpty() -> {
                Toast.makeText(this, "Username cannot be empty.", Toast.LENGTH_SHORT).show()
                false
            }
            bio.isEmpty() -> {
                Toast.makeText(this, "Bio cannot be empty.", Toast.LENGTH_SHORT).show()
                false
            }
            selectedInterests.size < minInterests -> {
                Toast.makeText(this, "Select at least $minInterests interests.", Toast.LENGTH_SHORT).show()
                false
            }
            imageUris.filterNotNull().size < minImages -> {
                Toast.makeText(this, "Select at least $minImages images.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun getSelectedInterests(): List<String> {
        val selectedInterests = mutableListOf<String>()
        if (binding.checkboxMovie.isChecked) selectedInterests.add("Interest 1")
        if (binding.checkboxArt.isChecked) selectedInterests.add("Interest 2")
        if (binding.checkboxMusic.isChecked) selectedInterests.add("Interest 3")
        if (binding.checkboxDesign.isChecked) selectedInterests.add("Interest 4")
        if (binding.checkboxCooking.isChecked) selectedInterests.add("Interest 5")
        if (binding.checkboxBooksReading.isChecked) selectedInterests.add("Interest 6")
        if (binding.checkboxSwimming.isChecked) selectedInterests.add("Interest 7")
        if (binding.checkboxPhotography.isChecked) selectedInterests.add("Interest 8")
        if (binding.checkboxSnooker.isChecked) selectedInterests.add("Interest 9")
        if (binding.checkboxGaming.isChecked) selectedInterests.add("Interest 10")
        if (binding.checkboxTraveling.isChecked) selectedInterests.add("Interest 11")
        if (binding.checkboxShopping.isChecked) selectedInterests.add("Interest 12")

        return selectedInterests
    }

    private fun saveProfileToDatabase() {
        val name = binding.nameEdit.text.toString().trim()
        val username = binding.usernameEdit.text.toString().trim()
        val bio = binding.aboutEdit.text.toString().trim()

        val selectedInterests = getSelectedInterests()
        val validImages = imageUris.filterNotNull()

        // database
        saveToDatabase(name, username, selectedInterests,bio, validImages)

        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun fetchImagesFromDatabase(): List<Uri> {
        //  database
        return listOf()
    }

    private fun saveToDatabase(name: String, username: String ,interests: List<String>,bio:String, images: List<Uri>) {
        // database
    }
}

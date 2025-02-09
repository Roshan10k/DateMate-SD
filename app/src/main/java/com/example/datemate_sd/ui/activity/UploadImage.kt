package com.example.datemate_sd.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityUploadImageBinding
import com.example.datemate_sd.model.UserModel
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.utils.ImageUtils
import com.example.datemate_sd.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class UploadImage : AppCompatActivity() {

    lateinit var binding: ActivityUploadImageBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userModel: UserModel // Store the UserModel instance
    private var userId: String? = null

    lateinit var imageUtils: ImageUtils
    private var selectedImageView: ImageView? = null
    private var imageUri1: Uri? = null
    private var imageUri2: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtils(this)

        imageUtils.registerActivity { url ->
            url?.let {
                when (selectedImageView) {
                    binding.addImage1 -> {
                        imageUri1 = it
                        Picasso.get().load(it).into(binding.addImage1)
                    }
                    binding.addImage2 -> {
                        imageUri2 = it
                        Picasso.get().load(it).into(binding.addImage2)
                    }
                }
            }
        }

        binding.addImage1.setOnClickListener {
            selectedImageView = binding.addImage1
            imageUtils.launchGallery(this)
        }

        binding.addImage2.setOnClickListener {
            selectedImageView = binding.addImage2
            imageUtils.launchGallery(this)
        }

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)
        userModel = intent.getParcelableExtra("User_Model") ?: UserModel()
        userId = userModel.UserId

        binding.galleryImageSaveButton.setOnClickListener{
            uploadImages()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadImages() {
        if (imageUri1 == null && imageUri2 == null) {
            Toast.makeText(this, "Please select images to upload", Toast.LENGTH_SHORT).show()
            return
        }

        var uploadedImageUrl1: String? = null
        var uploadedImageUrl2: String? = null

        val uploadCallback: (String?) -> Unit = { imageUrl ->
            if (imageUrl != null) {
                if (uploadedImageUrl1 == null) {
                    uploadedImageUrl1 = imageUrl
                } else {
                    uploadedImageUrl2 = imageUrl
                }

                if (uploadedImageUrl1 != null && uploadedImageUrl2 != null) {
                    addUser(uploadedImageUrl1!!, uploadedImageUrl2!!)
                }
            } else {
                Log.e("Upload Error", "Failed to upload image")
            }
        }

        imageUri1?.let { userViewModel.uploadImage(this, it, uploadCallback) }
        imageUri2?.let { userViewModel.uploadImage(this, it, uploadCallback) }
    }

    private fun addUser(url1: String, url2: String) {
        userModel.galleryImage1 = url1
        userModel.galleryImage2 = url2

        userViewModel.addUserToDatabase(userModel.UserId, userModel) { isSuccess, message ->
            if (isSuccess) {
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Error in registration: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

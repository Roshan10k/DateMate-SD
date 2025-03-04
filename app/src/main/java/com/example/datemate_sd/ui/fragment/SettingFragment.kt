package com.example.datemate_sd.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.FragmentSettingBinding
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.ui.activity.EditProfileActivity
import com.example.datemate_sd.ui.activity.LoginActivity
import com.example.datemate_sd.ui.activity.TermsAndConditionActivity
import com.example.datemate_sd.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class SettingFragment : Fragment() {
    lateinit var  binding: FragmentSettingBinding
    lateinit var  userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfileLayout.setOnClickListener{
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.logoutBtn.setOnClickListener {
            // Clear session data (e.g., SharedPreferences)
            val sharedPrefs = requireActivity().getSharedPreferences("userPrefs", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.clear()  // Clear all data stored
            editor.apply()

            // Firebase logout (if you're using Firebase)
            FirebaseAuth.getInstance().signOut()

            // Show logout success toast
            Toast.makeText(requireContext(), "Logout successful", Toast.LENGTH_SHORT).show()

            // Navigate to Login screen
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

            // Close current activity to prevent navigating back to the setting screen
            requireActivity().finish()
        }




        binding.termsArrowImage.setOnClickListener {
            val intent = Intent(requireContext(), TermsAndConditionActivity::class.java)
            startActivity(intent)
        }

        var currentUser = userViewModel.getCurrentUser()

        currentUser.let { 
            userViewModel.getUserFromDatabase((it?.uid.toString()))

        }

        userViewModel.users.observe(requireActivity()){
            binding.nameSetting.text = it?.name.toString()
            binding.userEmail.text = it?.emailAddress.toString()
            Picasso.get().load(it?.imageurl).placeholder(R.drawable.baseline_person_24)
                .into(binding.settingProfileImage)


        }


    }



}
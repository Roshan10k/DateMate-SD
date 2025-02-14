package com.example.datemate_sd.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.FragmentSettingBinding
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.viewmodel.UserViewModel
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

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

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
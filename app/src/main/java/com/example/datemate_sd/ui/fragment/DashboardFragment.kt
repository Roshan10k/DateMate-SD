package com.example.datemate_sd.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datemate_sd.adapter.DashboardRecyclerAdapter
import com.example.datemate_sd.databinding.FragmentDashboardBinding
import com.example.datemate_sd.repository.UserRepositoryImpl
//import com.example.datemate_sd.ui.activity.NotificationActivity
import com.example.datemate_sd.viewmodel.UserViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.example.datemate_sd.R
import com.example.datemate_sd.ui.activity.NotificationActivity

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    lateinit var userViewModel: UserViewModel

    private lateinit var DashboardRecyclerAdapter: DashboardRecyclerAdapter


    companion object {
        private const val TAG = "DashboardFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)


        userViewModel.saveUserFCMToken()

        DashboardRecyclerAdapter = DashboardRecyclerAdapter(requireContext(), ArrayList())
        userViewModel.getAllUserFunc()

        userViewModel.getAllUsers.observe(viewLifecycleOwner){
            it?.let {
                DashboardRecyclerAdapter.updateData(it)
            }
        }
        binding.dashboardRecyclerView.adapter = DashboardRecyclerAdapter
        binding.dashboardRecyclerView.apply {
            adapter = DashboardRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }


        userViewModel.loading.observe(viewLifecycleOwner){loading->
            if (loading){
                binding.dashboardLoadingProgressBar.visibility = View.VISIBLE
            }else{
                binding.dashboardLoadingProgressBar.visibility = View.GONE

            }
        }


//      to see the token of login user
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log the token
            Log.d(TAG, "FCM Token: $token")
        }

        binding.noti.setOnClickListener{
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity((intent))
        }

    }

}

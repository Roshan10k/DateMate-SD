package com.example.datemate_sd.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datemate_sd.adapter.DashboardRecyclerAdapter
import com.example.datemate_sd.databinding.FragmentDashboardBinding
import com.example.datemate_sd.repository.UserRepositoryImpl
import com.example.datemate_sd.ui.activity.NotificationActivity
import com.example.datemate_sd.viewmodel.UserViewModel

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    lateinit var userViewModel: UserViewModel

    private lateinit var DashboardRecyclerAdapter: DashboardRecyclerAdapter

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

        binding.noti.setOnClickListener{
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity((intent))
        }

    }

}

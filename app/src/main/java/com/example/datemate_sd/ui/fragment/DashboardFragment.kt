package com.example.datemate_sd.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datemate_sd.Adapter.DashboardRecyclerAdapter
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.FragmentDashboardBinding
import com.example.datemate_sd.ui.activity.NotificationActivity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val imagelist = arrayListOf(
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
        R.drawable.sampleperson1,
    )

    private val namelist = arrayListOf(
        "Kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
        "kiara",
    )

    private val agelist = arrayListOf(
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30",
        "30"
    )

    private val profession = arrayListOf(
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress",
        "Actress"
    )

    private lateinit var DashboardRecyclerAdapter: DashboardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DashboardRecyclerAdapter = DashboardRecyclerAdapter(requireContext(), imagelist, namelist, agelist,profession)

        binding.recyclerView.apply {
            adapter = DashboardRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        binding.noti.setOnClickListener{
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity((intent))
        }

    }

}

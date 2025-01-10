package com.example.datemate_sd.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datemate_sd.Adapter.DashboardRecyclerAdapter
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.FragmentDashboardBinding

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
        "Kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
        "kiara Adwani",
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
    }


    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

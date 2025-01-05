package com.example.datemate_sd.ui.activity
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datemate_sd.R
import com.example.datemate_sd.databinding.ActivityNavigationBinding
import com.example.datemate_sd.ui.fragment.DashboardFragment
import com.example.datemate_sd.ui.fragment.SearchFragment

class NavigationActivity : AppCompatActivity() {
    lateinit var binding:  ActivityNavigationBinding

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout , fragment)
        fragmentTransaction.commit()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.setOnItemSelectedListener{menu ->
            when(menu.itemId){
                R.id.navHome -> replaceFragment(DashboardFragment())
                    R.id.navSearch -> replaceFragment(SearchFragment())
                        else -> {}
            }
            true
        }
       ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
}
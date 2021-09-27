package com.example.ult3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val lm=Latest_Movies()
    private val tr=Top_Rated()
    private val uc=Upcoming()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragments(lm)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemReselectedListener {
            when(it.itemId)
            {
                R.id.lm -> fragments(lm)
                R.id.tr -> fragments(tr)
                R.id.uc -> fragments(uc)
            }
        }
    }

    private fun fragments(fragment: Fragment)
    {
        if(fragment!=null)
        {
            val display=supportFragmentManager.beginTransaction()
            display.replace(R.id.frame_layout,fragment)
            display.commit()
        }
    }

}
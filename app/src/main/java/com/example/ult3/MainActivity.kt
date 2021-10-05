package com.example.ult3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var s:Int = 0
    private val lm = Latest_Movies()
    private val tr = Top_Rated()
    private val uc = Upcoming()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragments(lm)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.lm -> fragments(lm)
                R.id.tr -> fragments(tr)
                R.id.uc -> fragments(uc)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.a_sort -> {
//                T
//            }
//            R.id.r_sort -> {
//            }
            R.id.r_7 -> {
//                s = 1
//                send_data(s)
                return true
            }
            R.id.r_5 -> {
//                s = 2
//                send_data(s)
                return true
            }
            R.id.all -> {
//                s = 3
//                send_data(s)
                return true
            }
            else -> super.onOptionsItemSelected(item)
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

    private fun send_data(s:Int){
//        val mFragmentManager = supportFragmentManager
//        val mFragmentTransaction = mFragmentManager.beginTransaction()
//        val mFragment = MyCustomFragment()
        val mBundle = Bundle()
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
        mBundle.putString("filter", s.toString())
        Latest_Movies().arguments = mBundle
        supportFragmentManager.beginTransaction().add(Latest_Movies(),"filter").commit()
    }

}
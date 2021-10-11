package com.example.ult3

import android.content.Intent
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
    private val fv = Favourites()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val i: Intent = intent
        val email = i.getStringExtra("email")
        if (email != null) {
            fragments(lm,email)
        }


        if(email != null) {
            val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigation.setOnItemReselectedListener {
                when (it.itemId) {
                    R.id.lm -> fragments(lm, email)
                    R.id.tr -> fragments(tr, email)
                    R.id.uc -> fragments(uc, email)
                    R.id.fv -> fragments(fv, email)
                }
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

//    fun newInstance(email:String,):Fragment{
//        val i1 = Latest_Movies()
//        val bundle = Bundle()
//        bundle.putString("email",email)
//        i1.arguments = bundle
//        return i1
//    }

    private fun fragments(fragment: Fragment,email: String)
    {
        if(fragment!=null)
        {
            val i1 = fragment
            val bundle = Bundle()
            bundle.putString("email",email)
            i1.arguments = bundle
            val display=supportFragmentManager.beginTransaction()
            display.replace(R.id.frame_layout,fragment)
            display.commit()
//            Toast.makeText(this, i1.arguments?.getString("email"),Toast.LENGTH_SHORT).show()

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
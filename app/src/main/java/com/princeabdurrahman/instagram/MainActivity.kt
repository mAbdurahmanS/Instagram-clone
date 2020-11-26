package com.princeabdurrahman.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.princeabdurrahman.instagram.Fragment.HomeFragment
import com.princeabdurrahman.instagram.Fragment.NotificationFragment
import com.princeabdurrahman.instagram.Fragment.ProfileFragment
import com.princeabdurrahman.instagram.Fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    //untuk mengaktifkan bottomNavigation di Activity
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.nav_home ->{
                moveTofragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_search ->{
                moveTofragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_add_post ->{
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_notification ->{
                moveTofragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_profile ->{
                moveTofragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //untuk membuild bottom navigationnya
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //supaya home menjadi defaultketika aplikasi
        moveTofragment(HomeFragment())
    }

    //function untuk pindah antar fragment
    private fun moveTofragment(fragment: Fragment){
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }
}
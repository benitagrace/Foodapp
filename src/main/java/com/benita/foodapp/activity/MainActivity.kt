package com.benita.foodapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.benita.foodapp.R
import com.benita.foodapp.fragment.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frameLayout)
        navigationView=findViewById(R.id.navigationView)

        setUpToolbar()

       openHome()

        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem != null){

                previousMenuItem?.isChecked= false
            }

            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId){

                R.id.appHome ->{

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            HomeFragment()
                        )

                        .commit()
                    supportActionBar?.title="All Restaurants"
                    drawerLayout.closeDrawers()
                }
                R.id.myProfile ->{

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            ProfileFragment()
                        )

                        .commit()
                    supportActionBar?.title="My Profile"
                    drawerLayout.closeDrawers()


                }
                R.id.favRes -> {

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            FavouriteFragment()
                        )

                        .commit()
                    supportActionBar?.title="Favourite Restaurants"
                    drawerLayout.closeDrawers()

                }
                 R.id.orderHistory ->{

                     supportFragmentManager.beginTransaction()
                         .replace(
                             R.id.frameLayout,
                             OrderFragment()
                         )

                         .commit()
                     supportActionBar?.title="Order History"
                     drawerLayout.closeDrawers()


                 }
                R.id.faqs ->{

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                           FaqFragment()
                        )

                        .commit()
                    supportActionBar?.title="FAQs"
                    drawerLayout.closeDrawers()


                }
                R.id.logOut ->{

                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            LogoutFragment()
                        )

                        .commit()
                    supportActionBar?.title="Log Out"
                    drawerLayout.closeDrawers()


                }


            }

            return@setNavigationItemSelectedListener true
        }


    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

        if(id == android.R.id.home){

            drawerLayout.openDrawer(GravityCompat.START)
        }

        return  super.onOptionsItemSelected(item)
    }

    fun openHome(){

        val fragment=HomeFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
        supportActionBar?.title="All Restaurants"
        navigationView.setCheckedItem(R.id.appHome)
    }

    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frameLayout)

        when(frag){

            !is HomeFragment-> openHome()
            else -> super.onBackPressed()
        }
    }
}

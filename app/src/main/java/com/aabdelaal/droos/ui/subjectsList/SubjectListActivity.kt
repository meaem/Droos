package com.aabdelaal.droos.ui.subjectsList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.aabdelaal.droos.R
import com.aabdelaal.droos.databinding.ActivitySubjectListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SubjectListActivity : AppCompatActivity() {

    private lateinit var databinding: ActivitySubjectListBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = ActivitySubjectListBinding.inflate(layoutInflater)

        setContentView(databinding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(com.aabdelaal.droos.R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//        navController.addOnDestinationChangedListener {
//
//        }
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)

        //associate the bottom navigation with the navController
        setupWithNavController(bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}
package com.aabdelaal.droos.ui.subjectsList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.aabdelaal.droos.R
import com.aabdelaal.droos.databinding.ActivitySubjectListBinding

class SubjectListActivity : AppCompatActivity() {

    private lateinit var databinding: ActivitySubjectListBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = ActivitySubjectListBinding.inflate(layoutInflater)

        setContentView(databinding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
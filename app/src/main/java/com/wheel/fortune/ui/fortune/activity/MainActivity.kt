package com.wheel.fortune.ui.fortune.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.wheel.fortune.R
import com.wheel.fortune.databinding.ActivityMainBinding
import com.wheel.fortune.utils.AppUtils


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }

    override fun onBackPressed() {
        when {
            findNavController(R.id.nav_host).graph.startDestination == findNavController(R.id.nav_host).currentDestination?.id ->
            {
                super.onBackPressed()
            }
            findNavController(R.id.nav_host).currentDestination!!.id == R.id.game ->{
                if (doubleBackToExitPressedOnce) {
                    finish()
                }

                doubleBackToExitPressedOnce = true
                AppUtils.showToastMsg(this, "Please click back again to exit")

                Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
            }
            else -> Navigation.findNavController(this, R.id.nav_host).popBackStack(R.id.game, true)
        }

    }
}
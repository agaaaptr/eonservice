package com.example.eonservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.eonservice.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        supportActionBar?.hide()

        serviceDeviceListener()
        initialDiagnosisListener()
        referenceListener()
        settingsListener()
        historyListener()

        binding.btmNavbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> replaceFragment(Home())
                R.id.nav_profile -> replaceFragment(com.example.eonservice.Profile())

                else ->{

                }
            }
            true
        }
    }

    private fun historyListener() {
        layout_history.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }

    private fun settingsListener() {
        image_menu.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }


    private fun referenceListener() {
        layout_reference.setOnClickListener {
            startActivity(Intent(this, ReferenceActivity::class.java))
        }
    }

    private fun initialDiagnosisListener() {
        layout_diagnosis.setOnClickListener {
            startActivity(Intent(this, DiagnosisActivity::class.java))
        }
    }

    private fun serviceDeviceListener() {
        layout_service.setOnClickListener {
            startActivity(Intent(this, ServiceActivity::class.java))
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl,fragment)
        fragmentTransaction.commit()

    }
}
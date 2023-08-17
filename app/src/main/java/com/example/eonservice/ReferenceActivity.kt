package com.example.eonservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reference.*

class ReferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)
        supportActionBar?.hide()

        btnBackListener()
    }

    private fun btnBackListener() {
        btn_back3.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
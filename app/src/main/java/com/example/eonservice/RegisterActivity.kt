package com.example.eonservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        R_btn_1.setOnClickListener {
            val email = R_edt_email.text.toString().trim()
            val password = R_edt_password.text.toString().trim()

            if (email.isEmpty()){
                R_edt_email.error = "Email can not be empty!"
                R_edt_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                R_edt_email.error = "Email is not valid"
                R_edt_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6){
                R_edt_password.error = "Password must be more than 6 characters!"
                R_edt_password.requestFocus()
                return@setOnClickListener
            }

            registerUser(email, password)
        }
        btnBackRegisterListener()
        txtLoginListener()
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@RegisterActivity, HomeActivity::class.java).also {intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@RegisterActivity, HomeActivity::class.java).also {intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun btnBackRegisterListener() {
        R_img_1.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun txtLoginListener() {
        txt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
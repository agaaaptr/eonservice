package com.example.eonservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance("https://eonservice-e3c94-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val usersRef = database.getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        R_btn_1.setOnClickListener {
            val email = R_edt_email.text.toString().trim()
            val password = R_edt_password.text.toString().trim()

            if (email.isEmpty()) {
                R_edt_email.error = "Email tidak boleh kosong!"
                R_edt_email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                R_edt_email.error = "Email tidak valid"
                R_edt_email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                R_edt_password.error = "Password harus lebih dari 6 karakter!"
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
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val user = User(userId, email, password)

                    usersRef.child(userId).setValue(user)
                        .addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                Intent(this@RegisterActivity, HomeActivity::class.java).also { intent ->
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                            } else {
                                Toast.makeText(this, "Gagal menyimpan data user", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        showEmailAlreadyInUseDialog()
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun showEmailAlreadyInUseDialog() {
        AlertDialog.Builder(this)
            .setTitle("Email sudah terdaftar")
            .setMessage("Email ini sudah terdaftar. Silakan gunakan email lain.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            Intent(this@RegisterActivity, HomeActivity::class.java).also { intent ->
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

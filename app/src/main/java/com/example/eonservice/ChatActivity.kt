package com.example.eonservice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()

        // Panggil fungsi mainButton di dalam onCreate
        mainButton(btn_url)

        // Panggil fungsi untuk handle klik pada tombol back
        handleBackButton()

    }

    private fun mainButton(button: Button) {
        button.setOnClickListener {
            openWhatsAppWithAdmin()
        }
    }

    private fun openWhatsAppWithAdmin() {
        try {
            // Nomor WhatsApp admin yang akan dihubungi
            val adminPhoneNumber = "+6285175202875" // Ganti dengan nomor WhatsApp admin yang sesuai
            val contactNumber = "Admin"
            val message = "Halo ${contactNumber}!, saya membutuhkan bantuan."

            // Membuat URI untuk memulai WhatsApp
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=${adminPhoneNumber}&text=${message}")

            // Membuat Intent untuk membuka WhatsApp
            val intent = Intent(Intent.ACTION_VIEW, uri)

            // Memulai aplikasi WhatsApp
            startActivity(intent)
        } catch (e: Exception) {
            // Handle jika terjadi kesalahan
            e.printStackTrace()
        }
    }

    private fun handleBackButton() {
        C_img_1.setOnClickListener {
            // Ketika gambar back ditekan, arahkan ke HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}

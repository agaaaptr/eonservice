package com.example.eonservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.eonservice.databinding.ActivityServiceBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceBinding
    private lateinit var database: DatabaseReference
    private lateinit var db: FirebaseDatabase
    private lateinit var edtName: EditText
    private lateinit var edtContact: EditText
    private lateinit var edtBrand: EditText
    private lateinit var edtSeries: EditText
    private lateinit var edtProblem: EditText
    private lateinit var edtDiagnosa: EditText
    private lateinit var btnOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        supportActionBar?.hide()

        edtName = findViewById(R.id.form_name)
        edtContact = findViewById(R.id.form_contact)
        edtBrand = findViewById(R.id.form_brand)
        edtSeries = findViewById(R.id.form_series)
        edtProblem = findViewById(R.id.form_problem)
        edtDiagnosa = findViewById(R.id.form_hasilDiagnosa)
        btnOrder = findViewById(R.id.btn_order)

        database = FirebaseDatabase.getInstance("https://eonservice-e3c94-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Transaction")

        btnBackListener()

        btnOrder.setOnClickListener {
            saveTransData()
            orderSuccess()
        }
    }

    private fun btnBackListener() {
        back_img2.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun orderSuccess() {
        startActivity(Intent(this, TaskSuccessActivity::class.java))
    }

    private fun saveTransData() {

        val nama = edtName.text.toString()
        val contact = edtContact.text.toString()
        val brand = edtBrand.text.toString()
        val problem = edtProblem.text.toString()
        val series = edtSeries.text.toString()
        val diagnosa = edtDiagnosa.text.toString()

        if (nama.isEmpty()){
            edtName.error = "Mohon masukkan nama anda"
            edtName.requestFocus()
            return
        }

        if (contact.isEmpty()){
            edtName.error = "Mohon masukkan kontak WhatsApp"
            edtName.requestFocus()
            return
        }

        if (brand.isEmpty()){
            edtBrand.error = "Mohon masukkan merek laptop"
            edtBrand.requestFocus()
            return
        }

        if (series.isEmpty()){
            edtSeries.error = "Mohon masukkan seri laptop"
            edtSeries.requestFocus()
            return
        }

        if (problem.isEmpty()){
            edtProblem.error = "Mohon beritahu permasalahan laptop"
            edtProblem.requestFocus()
            return
        }

        if (diagnosa.isEmpty()){
            edtProblem.error = "Mohon beritahu hasil diagnosa awal"
            edtProblem.requestFocus()
            return
        }

        val trId = database.push().key!!

        val trans = TransactionModel(nama, contact, brand, series, problem, diagnosa)
        database.child(trId).setValue(trans).addOnSuccessListener {

            edtName.text.clear()
            edtContact.text.clear()
            edtBrand.text.clear()
            edtSeries.text.clear()
            edtProblem.text.clear()
            edtDiagnosa.text.clear()
        }
    }
}
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
    private lateinit var edtBrand: EditText
    private lateinit var edtSeries: EditText
    private lateinit var edtProblem: EditText
    private lateinit var btnOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        supportActionBar?.hide()

        edtBrand = findViewById(R.id.form_brand)
        edtSeries = findViewById(R.id.form_series)
        edtProblem = findViewById(R.id.form_problem)
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

        val brand = edtBrand.text.toString()
        val problem = edtProblem.text.toString()
        val series = edtSeries.text.toString()

        if (brand.isEmpty()){
            edtBrand.error = "Please enter laptop brand"
            edtBrand.requestFocus()
            return
        }

        if (series.isEmpty()){
            edtSeries.error = "Please enter laptop series"
            edtSeries.requestFocus()
            return
        }

        if (problem.isEmpty()){
            edtProblem.error = "Please enter your problem"
            edtProblem.requestFocus()
            return
        }

        val trId = database.push().key!!

        val trans = TransactionModel(trId, brand, series, problem)
        database.child(trId).setValue(trans).addOnSuccessListener {

            edtBrand.text.clear()
            edtSeries.text.clear()
            edtProblem.text.clear()
        }
    }
}
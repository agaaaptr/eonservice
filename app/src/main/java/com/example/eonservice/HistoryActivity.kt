package com.example.eonservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var historyRecyclerView : RecyclerView
    private lateinit var historyArrayList : ArrayList<TransactionModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.hide()

        btnBackListener()

        historyRecyclerView = findViewById(R.id.trans_list)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.setHasFixedSize(true)

        historyArrayList = arrayListOf<TransactionModel>()
        getTransData()

    }

    private fun getTransData() {

        database = FirebaseDatabase.getInstance("https://eonservice-e3c94-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Transaction")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (historySnapshot in snapshot.children){

                        val history = historySnapshot.getValue(TransactionModel::class.java)
                        historyArrayList.add(history!!)

                    }

                    historyRecyclerView.adapter = ListAdapter(historyArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

    private fun btnBackListener() {
        back_img3.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
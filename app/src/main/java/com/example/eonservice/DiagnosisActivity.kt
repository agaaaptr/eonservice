package com.example.eonservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_diagnosis.*

class DiagnosisActivity : AppCompatActivity() {

    private lateinit var cpuInput: EditText
    private lateinit var ramInput: EditText
    private lateinit var hardDiskInput: EditText
    private lateinit var diagnoseButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosis)
        supportActionBar?.hide()

        cpuInput = findViewById(R.id.cpu_input)
        ramInput = findViewById(R.id.ram_input)
        hardDiskInput = findViewById(R.id.hard_disk_input)
        diagnoseButton = findViewById(R.id.diagnose_button)
        resultTextView = findViewById(R.id.result_text)

        diagnoseButton.setOnClickListener {
            val cpuText = cpuInput.text.toString()
            val ramText = ramInput.text.toString()
            val hardDiskText = hardDiskInput.text.toString()

            if (cpuText.isEmpty() || ramText.isEmpty() || hardDiskText.isEmpty()) {
                resultTextView.text = "Insert the value issues first"
            } else {
                val cpuValue = cpuText.toDouble()
                val ramValue = ramText.toDouble()
                val hardDiskValue = hardDiskText.toDouble()

                val result = performDiagnosis(cpuValue, ramValue, hardDiskValue)
                resultTextView.text = result
            }
        }


        btnBackListener()

    }

    private fun btnBackListener() {
        back_img_diagnosis.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun performDiagnosis(cpuValue: Double, ramValue: Double, hardDiskValue: Double): String {
        // Perform AHP calculations to get the diagnosis result

        val average = (cpuValue + ramValue + hardDiskValue) / 3

        return when {
            cpuValue == ramValue && cpuValue == hardDiskValue -> "Karena nilainya sama, semua komponen mungkin bermasalah, tetapi CPU lebih mungkin"
            average > 3 && cpuValue == ramValue && cpuValue == hardDiskValue -> "Karena nilainya sama, semua komponen mungkin bermasalah, tetapi CPU lebih mungkin"
            cpuValue > hardDiskValue && cpuValue > ramValue -> "Perangkat mengalami masalah CPU"
            hardDiskValue > cpuValue && hardDiskValue > ramValue -> "Perangkat mengalami masalah Hard Disk"
            ramValue > cpuValue && ramValue > hardDiskValue -> "Perangkat mengalami masalah RAM"
            average > 3 && cpuValue == hardDiskValue -> "Perangkat mengalami masalah CPU"
            average > 3 && hardDiskValue == ramValue -> "Perangkat mengalami masalah Hard Disk"
            average > 3 && cpuValue == ramValue -> "Perangkat mengalami masalah CPU"
            average > 10 -> "Nilai rata-ratanya lebih dari 10, semua komponen mungkin mempunyai nilai permasalahan kritis yang sama"

            else -> "Masalah tidak diketahui"
        }
    }



}

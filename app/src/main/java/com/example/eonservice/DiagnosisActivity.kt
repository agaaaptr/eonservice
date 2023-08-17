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
            cpuValue == ramValue && cpuValue == hardDiskValue -> "Since the value is the same, all the components may have issues, but the CPU is more possible"
            average > 3 && cpuValue == ramValue && cpuValue == hardDiskValue -> "Since the value is the same, all the components may have issues, but the CPU is more possible"
            cpuValue > hardDiskValue && cpuValue > ramValue -> "Device has CPU issues"
            hardDiskValue > cpuValue && hardDiskValue > ramValue -> "Device has Hard Disk issues"
            ramValue > cpuValue && ramValue > hardDiskValue -> "Device has RAM issues"
            average > 3 && cpuValue == hardDiskValue -> "Device has CPU issues"
            average > 3 && hardDiskValue == ramValue -> "Device has Hard Disk issues"
            average > 3 && cpuValue == ramValue -> "Device has CPU issues"
            average > 10 -> "The average value is more than 10, all the components may have same valuable critical issues"

            else -> "Unknown issue"
        }
    }



}

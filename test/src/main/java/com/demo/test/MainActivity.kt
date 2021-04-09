package com.demo.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.test)

        val test = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        button.setOnClickListener {
            test.launch(arrayOf("image/*"))
        }
    }
}
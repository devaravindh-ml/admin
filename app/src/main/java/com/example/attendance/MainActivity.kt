package com.example.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
// 1. IMPORT the generated binding class. Using the correct name: ActivityMainBinding.
import com.example.attendance.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // 2. DECLARE the binding object. Using the correct name: ActivityMainBinding.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // OLD WAY (commented out)
        // setContentView(R.layout.activity_main)

        // NEW WAY: Inflate the layout using the requested binding class
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // OLD WAY (commented out)
        // val studentLoginButton = findViewById<Button>(R.id.btn_student_login)

        // NEW WAY: Access the view directly through the binding object
        binding.btnStudentLogin.setOnClickListener {

            val intent = Intent(this, StudentLogin::class.java)

            startActivity(intent)
        }
    }
}

package com.example.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class Student : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student)

        val logoutbutton = findViewById<Button>(R.id.btn_logout)

        logoutbutton.setOnClickListener {
            val intent = Intent(this, StudentLogin::class.java)

            startActivity(intent)
        }


    }


}

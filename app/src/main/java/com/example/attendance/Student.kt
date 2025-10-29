package com.example.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class Student : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student)

        // --- 1. Logout Button Setup ---
        // This button now functions independently.
        val button = findViewById<Button>(R.id.btn_logout)
         button.setOnClickListener {
            // Assuming 'StudentLogin' is the target activity for logging out.
            val intent = Intent(this, StudentLogin::class.java)
            startActivity(intent)

            finish()
            // It's good practice to close the current activity when logging out.

        }

        // --- 2. View Attendance Button Setup ---
        // Moved the attendance button setup OUTSIDE the logout listener.
        val viewAttendanceButton = findViewById<Button>(R.id.btn_view_attendance)
        viewAttendanceButton.setOnClickListener {
            // It navigates to your attendance details screen.
            val intent = Intent(this,Attendance::class.java)
            startActivity(intent)





        }
    }
}

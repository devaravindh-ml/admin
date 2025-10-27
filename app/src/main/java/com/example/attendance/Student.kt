package com.example.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
// Note: Assuming AttendanceActivity and StudentLogin are in the same package or imported correctly.
import com.example.attendance.AttendanceActivity

class Student : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student)

        // --- 1. Logout Button Setup ---
        // This button now functions independently.
        val logoutbutton = findViewById<Button>(R.id.btn_logout)
        logoutbutton.setOnClickListener {
            // Assuming 'StudentLogin' is the target activity for logging out.
            val intent = Intent(this, StudentLogin::class.java)
            startActivity(intent)
            // It's good practice to close the current activity when logging out.
            finish()
        }

        // --- 2. View Attendance Button Setup ---
        // Moved the attendance button setup OUTSIDE the logout listener.
        val viewAttendanceButton = findViewById<Button>(R.id.btn_view_attendance)
        viewAttendanceButton.setOnClickListener {
            // It navigates to your attendance details screen.
            val intent = Intent(this, AttendanceActivity::class.java)
            startActivity(intent)
        }

        // --- 3. View Homework Button Setup (for completeness) ---
        val viewHomeworkButton = findViewById<Button>(R.id.btn_view_homework)
        viewHomeworkButton.setOnClickListener {
            // Add Intent to Homework Activity here when you create it.
            // Example: Toast.makeText(this, "Opening Homework...", Toast.LENGTH_SHORT).show()
        }
    }
}

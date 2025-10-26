package com.example.attendance

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Assume you have an activity named 'Student' (or whatever your dashboard is named)
class StudentLogin : AppCompatActivity() {

    // Removed unused import for VoiceInteractionSession

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_login)


        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        loginButton = findViewById(R.id.btn_login)

        loginButton.setOnClickListener {
            handleLogin()
        }


        loginButton.visibility = View.VISIBLE
    }


    private fun handleLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                this,
                "Please enter both email and password correctly.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Invalid email format"
            return
        }

        if (email == "student@school.com" && password == "12345") {
            // SUCCESSFUL LOGIN: Navigate to the 'Student' activity
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()

            val intent = Intent(this, Student::class.java)
            startActivity(intent)
            finish()

        } else {
            Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_LONG).show()
            passwordEditText.text.clear()
        }
    }
}
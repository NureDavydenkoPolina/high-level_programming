package com.example.socialapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.socialapp.R
import com.example.socialapp.models.User
import com.example.socialapp.storage.JsonManager
import android.content.Intent

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        val spinnerMood = findViewById<Spinner>(R.id.spinnerMood)

        val btnCreateProfile = findViewById<Button>(R.id.btnCreateProfile)

        val jsonManager = JsonManager(this)

        val intent = Intent(
            this,
            ProfileActivity::class.java
        )

        btnCreateProfile.setOnClickListener {

            val name = etName.text.toString()
            val username = etUsername.text.toString()
            val age = etAge.text.toString().toIntOrNull() ?: 0
            val description = etDescription.text.toString()
            val password = etPassword.text.toString()
            val mood = spinnerMood.selectedItem.toString()

            val user = User(
                name,
                username,
                age,
                description,
                password,
                mood
            )

            jsonManager.saveUser(user)

            Toast.makeText(
                this,
                "Profile created!",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(
                this,
                ProfileActivity::class.java
            )

            intent.putExtra("username", username)

            startActivity(intent)
        }
    }
}
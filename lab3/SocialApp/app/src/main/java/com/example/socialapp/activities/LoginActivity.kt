package com.example.socialapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.socialapp.R
import com.example.socialapp.storage.JsonManager

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername =
            findViewById<EditText>(R.id.etUsername)

        val etPassword =
            findViewById<EditText>(R.id.etPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val btnGoToRegister =
            findViewById<Button>(R.id.btnGoToRegister)

        val jsonManager = JsonManager(this)

        btnGoToRegister.setOnClickListener {

            val intent = Intent(
                this,
                RegisterActivity::class.java
            )

            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            val username =
                etUsername.text.toString()

            val password =
                etPassword.text.toString()

            val users =
                jsonManager.loadUsers()

            val currentUser = users.find {

                it.username == username &&
                        it.password == password
            }

            if (currentUser != null) {

                Toast.makeText(
                    this,
                    "Login successful!",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(
                    this,
                    ProfileActivity::class.java
                )

                intent.putExtra(
                    "username",
                    currentUser.username
                )

                startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "Wrong username or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
package com.example.socialapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.socialapp.R
import com.example.socialapp.models.User
import com.example.socialapp.storage.JsonManager

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val layoutUsersContainer =
            findViewById<LinearLayout>(
                R.id.layoutUsersContainer
            )

        val currentUsername =
            intent.getStringExtra("username")

        val jsonManager = JsonManager(this)

        val users = jsonManager.loadUsers()

        val currentUser = users.find {
            it.username == currentUsername
        }

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {

            finish()
        }

        for (user in users) {

            if (user.username == currentUsername) {
                continue
            }

            val tvUser = TextView(this)

            tvUser.text =
                """
                Name: ${user.name}
                Username: ${user.username}
                Age: ${user.age}
                Description: ${user.description}
                Mood: ${user.mood}
                """.trimIndent()

            tvUser.textSize = 18f

            val btnAddFriend = Button(this)

            btnAddFriend.text = "Add Friend"

            if (currentUser != null &&
                currentUser.friends.contains(user.username)
            ) {

                btnAddFriend.text = "Friend"

                btnAddFriend.isEnabled = false
            }

            btnAddFriend.setOnClickListener {

                if (currentUser != null) {

                    if (!currentUser.friends.contains(user.username)) {

                        currentUser.friends.add(user.username)

                        jsonManager.saveAllUsers(users)

                        Toast.makeText(
                            this,
                            "Friend added!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                            this,
                            "Already in friends",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            layoutUsersContainer.addView(tvUser)

            layoutUsersContainer.addView(btnAddFriend)
        }
    }
}
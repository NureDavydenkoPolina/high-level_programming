package com.example.socialapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.socialapp.R
import com.example.socialapp.storage.JsonManager
import android.widget.LinearLayout

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvMood: TextView
    private lateinit var tvPassword: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnUsers: Button
    private lateinit var layoutFriendsContainer: LinearLayout

    private var username: String? = null
    private lateinit var jsonManager: JsonManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvName = findViewById(R.id.tvName)
        tvUsername = findViewById(R.id.tvUsername)
        tvAge = findViewById(R.id.tvAge)
        tvDescription = findViewById(R.id.tvDescription)
        tvMood = findViewById(R.id.tvMood)
        tvPassword = findViewById(R.id.tvPassword)

        btnLogout = findViewById(R.id.btnLogout)
        btnUsers = findViewById(R.id.btnUsers)
        layoutFriendsContainer = findViewById(R.id.layoutFriendsContainer)

        username = intent.getStringExtra("username")
        jsonManager = JsonManager(this)

        val btnPosts =
            findViewById<Button>(R.id.btnPosts)

        val btnMessages =
            findViewById<Button>(R.id.btnMessages)

        btnPosts.setOnClickListener {

            val intent = Intent(
                this,
                PostsActivity::class.java
            )

            intent.putExtra(
                "username",
                username
            )

            startActivity(intent)
        }

        btnMessages.setOnClickListener {

            val intent = Intent(
                this,
                MessagesActivity::class.java
            )

            intent.putExtra(
                "username",
                username
            )

            startActivity(intent)
        }

        btnUsers.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadProfile()
    }

    override fun onResume() {
        super.onResume()
        loadProfile()
    }

    private fun loadProfile() {
        val currentUser = jsonManager.loadUsers().find {
            it.username == username
        }

        if (currentUser != null) {
            tvName.text = "Name: ${currentUser.name}"
            tvUsername.text = "Username: ${currentUser.username}"
            tvAge.text = "Age: ${currentUser.age}"
            tvDescription.text = "Description: ${currentUser.description}"
            tvMood.text = "Mood: ${currentUser.mood}"
            tvPassword.text = "Password: ${currentUser.password}"

            layoutFriendsContainer.removeAllViews()

            val users = jsonManager.loadUsers().toMutableList()

            for (friendUsername in currentUser.friends) {

                val friendLayout = LinearLayout(this)

                friendLayout.orientation =
                    LinearLayout.HORIZONTAL

                val tvFriend = TextView(this)

                tvFriend.text = friendUsername

                tvFriend.textSize = 18f

                val btnRemove = Button(this)

                btnRemove.text = "✕"

                btnRemove.textSize = 10f

                val params = LinearLayout.LayoutParams(
                    90,
                    90
                )

                params.setMargins(20, 0, 0, 0)

                btnRemove.layoutParams = params

                btnRemove.setPadding(0, 0, 0, 0)

                btnRemove.setBackgroundColor(
                    android.graphics.Color.parseColor("#EF5350")
                )

                btnRemove.setTextColor(
                    android.graphics.Color.WHITE
                )

                btnRemove.setOnClickListener {

                    val updatedUsers =
                        jsonManager.loadUsers().toMutableList()

                    val updatedCurrentUser =
                        updatedUsers.find {
                            it.username == username
                        }

                    updatedCurrentUser?.friends?.remove(
                        friendUsername
                    )

                    jsonManager.saveAllUsers(updatedUsers)

                    loadProfile()
                }

                friendLayout.addView(tvFriend)

                friendLayout.addView(btnRemove)

                layoutFriendsContainer.addView(friendLayout)
            }
        }
    }
}
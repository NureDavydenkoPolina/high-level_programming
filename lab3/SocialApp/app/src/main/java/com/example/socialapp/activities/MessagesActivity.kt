package com.example.socialapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.socialapp.R
import com.example.socialapp.models.Message
import com.example.socialapp.storage.JsonManager

class MessagesActivity : AppCompatActivity() {

    private lateinit var layoutFriendsChats:
            LinearLayout

    private lateinit var layoutMessagesContainer:
            LinearLayout

    private lateinit var etMessage:
            EditText

    private lateinit var btnSendMessage:
            Button

    private lateinit var jsonManager:
            JsonManager

    private var username: String? = null

    private var selectedFriend: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_messages)

        layoutFriendsChats =
            findViewById(R.id.layoutFriendsChats)

        layoutMessagesContainer =
            findViewById(R.id.layoutMessagesContainer)

        etMessage =
            findViewById(R.id.etMessage)

        btnSendMessage =
            findViewById(R.id.btnSendMessage)

        username =
            intent.getStringExtra("username")

        jsonManager = JsonManager(this)

        loadFriends()

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {

            finish()
        }

        btnSendMessage.setOnClickListener {

            val text =
                etMessage.text.toString()

            if (
                text.isNotEmpty() &&
                selectedFriend != null
            ) {

                val message = Message(
                    username ?: "Unknown",
                    selectedFriend!!,
                    text
                )

                jsonManager.saveMessage(message)

                etMessage.text.clear()

                loadChat()
            }
        }
    }

    private fun loadFriends() {

        layoutFriendsChats.removeAllViews()

        val users =
            jsonManager.loadUsers()

        val currentUser =
            users.find {
                it.username == username
            }

        currentUser?.friends?.forEach {

                friendUsername ->

            val btnFriend = Button(this)

            btnFriend.text = friendUsername

            btnFriend.setOnClickListener {

                selectedFriend =
                    friendUsername

                loadChat()
            }

            layoutFriendsChats
                .addView(btnFriend)
        }
    }

    private fun loadChat() {

        layoutMessagesContainer
            .removeAllViews()

        val messages =
            jsonManager.loadMessages()

        for (message in messages) {

            val isCurrentChat =

                (
                        message.sender == username &&
                                message.receiver ==
                                selectedFriend
                        )

                        ||

                        (
                                message.sender ==
                                        selectedFriend &&

                                        message.receiver ==
                                        username
                                )

            if (isCurrentChat) {

                val tvMessage =
                    TextView(this)

                tvMessage.text =
                    "${message.sender}: ${message.text}"

                tvMessage.textSize = 18f

                tvMessage.setPadding(
                    0,
                    15,
                    0,
                    15
                )

                layoutMessagesContainer
                    .addView(tvMessage)
            }
        }
    }
}
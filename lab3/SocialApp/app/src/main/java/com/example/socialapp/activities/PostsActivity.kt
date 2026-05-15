package com.example.socialapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.socialapp.R
import com.example.socialapp.models.Post
import com.example.socialapp.storage.JsonManager
import com.example.socialapp.models.Comment
import android.text.Editable
import android.text.TextWatcher

class PostsActivity : AppCompatActivity() {

    private lateinit var etPostText: EditText

    private lateinit var btnAddPost: Button

    private lateinit var layoutPostsContainer: LinearLayout

    private lateinit var jsonManager: JsonManager

    private var username: String? = null

    private lateinit var etSearchPosts: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_posts)

        etPostText =
            findViewById(R.id.etPostText)

        btnAddPost =
            findViewById(R.id.btnAddPost)

        layoutPostsContainer =
            findViewById(R.id.layoutPostsContainer)

        username =
            intent.getStringExtra("username")

        jsonManager = JsonManager(this)

        etSearchPosts =
            findViewById(R.id.etSearchPosts)

        loadPosts()

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {

            finish()
        }

        etSearchPosts.addTextChangedListener(

            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    loadPosts(
                        s.toString()
                    )
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {}
            }
        )

        btnAddPost.setOnClickListener {

            val text =
                etPostText.text.toString()

            if (text.isNotEmpty()) {

                val post = Post(
                    username ?: "Unknown",
                    text
                )

                jsonManager.savePost(post)

                etPostText.text.clear()

                loadPosts()
            }
        }
    }

    private fun loadPosts(searchText: String = "") {

        layoutPostsContainer.removeAllViews()

        val posts = jsonManager.loadPosts()

        for (post in posts.reversed()) {

            val matchesSearch =

                post.author.contains(
                    searchText,
                    ignoreCase = true
                )

                        ||

                        post.text.contains(
                            searchText,
                            ignoreCase = true
                        )

            if (!matchesSearch) {
                continue
            }

            val postLayout = LinearLayout(this)

            postLayout.orientation =
                LinearLayout.VERTICAL

            postLayout.setPadding(
                0,
                40,
                0,
                40
            )

            val tvPost = TextView(this)

            tvPost.text =
                "Author: ${post.author}\n\n${post.text}"

            tvPost.textSize = 18f

            postLayout.addView(tvPost)

            for (comment in post.comments) {

                val tvComment = TextView(this)

                tvComment.text =
                    ">> ${comment.author}: ${comment.text}"

                tvComment.textSize = 16f

                tvComment.setPadding(
                    40,
                    10,
                    0,
                    10
                )

                postLayout.addView(tvComment)
            }

            val etComment = EditText(this)

            etComment.hint = "Write comment..."

            postLayout.addView(etComment)

            val btnComment = Button(this)

            btnComment.text = "Add Comment"

            btnComment.setOnClickListener {

                val commentText =
                    etComment.text.toString().trim()

                if (commentText.isNotEmpty()) {

                    post.comments.add(

                        Comment(
                            username ?: "Unknown",
                            commentText
                        )
                    )

                    val updatedPosts =
                        jsonManager.loadPosts().toMutableList()

                    val index =
                        updatedPosts.indexOfFirst {

                            it.author == post.author &&
                                    it.text == post.text
                        }

                    if (index != -1) {

                        updatedPosts[index] = post

                        jsonManager.saveAllPosts(
                            updatedPosts
                        )

                        etComment.text.clear()

                        loadPosts(
                            etSearchPosts.text.toString()
                        )
                    }
                }
            }

            postLayout.addView(btnComment)

            layoutPostsContainer
                .addView(postLayout)
        }
    }
}
package com.example.socialapp.models

data class Post(
    val author: String,
    val text: String,
    var comments: MutableList<Comment> = mutableListOf()
)
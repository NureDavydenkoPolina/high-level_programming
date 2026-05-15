package com.example.socialapp.models

data class User(
    val name: String,
    val username: String,
    val age: Int,
    val description: String,
    val password: String,
    val mood: String?,
    val friends: MutableList<String> = mutableListOf()
)
package com.example.socialapp.storage

import android.content.Context
import com.example.socialapp.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import com.example.socialapp.models.Post
import com.example.socialapp.models.Message
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class JsonManager(private val context: Context) {

    private val fileName = "users.json"

    private val gson = Gson()

    fun saveUser(user: User) {

        val users = loadUsers().toMutableList()

        users.add(user)

        val json = gson.toJson(users)

        val file = File(context.filesDir, fileName)

        file.writeText(json)
    }

    fun loadUsers(): List<User> {

        val file = File(context.filesDir, fileName)

        if (!file.exists()) {
            return emptyList()
        }

        val json = file.readText()

        val type = object : TypeToken<List<User>>() {}.type

        return gson.fromJson(json, type)
    }

    fun saveAllUsers(users: List<User>) {

        val json = gson.toJson(users)

        val file = File(context.filesDir, fileName)

        file.writeText(json)
    }

    fun savePost(post: Post) {

        val posts = loadPosts().toMutableList()

        posts.add(post)

        saveAllPosts(posts)
    }

    fun loadPosts(): List<Post> {

        val file = File(context.filesDir, "posts.gz")

        if (!file.exists()) {
            return emptyList()
        }

        val inputStream =
            GZIPInputStream(file.inputStream())

        val json =
            inputStream.bufferedReader()
                .readText()

        inputStream.close()

        val type =
            object : TypeToken<List<Post>>() {}.type

        return gson.fromJson(json, type)
    }

    fun saveAllPosts(posts: List<Post>) {

        val json = gson.toJson(posts)

        val file = File(context.filesDir, "posts.gz")

        val outputStream =
            GZIPOutputStream(file.outputStream())

        outputStream.write(
            json.toByteArray()
        )

        outputStream.close()
    }

    fun saveMessage(message: Message) {

        val messages = loadMessages().toMutableList()

        messages.add(message)

        val json = gson.toJson(messages)

        val file = File(context.filesDir, "messages.json")

        file.writeText(json)
    }

    fun loadMessages(): List<Message> {

        val file = File(context.filesDir, "messages.json")

        if (!file.exists()) {
            return emptyList()
        }

        val json = file.readText()

        val type =
            object : TypeToken<List<Message>>() {}.type

        return gson.fromJson(json, type)
    }
}
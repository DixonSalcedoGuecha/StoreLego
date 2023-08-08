package com.example.storelego.repository

interface LoginRepository {
    suspend fun signIn (email: String, password: String): String
}
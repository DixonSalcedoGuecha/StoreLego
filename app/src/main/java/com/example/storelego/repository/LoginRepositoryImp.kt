package com.example.storelego.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginRepositoryImp @Inject constructor() : LoginRepository {
    override suspend fun signIn(email: String, password: String): String =
        suspendCoroutine { continuation ->

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    task.addOnCompleteListener {
                        if (it.isSuccessful) {

                            val user = FirebaseAuth.getInstance().currentUser
                            Log.d("", "signInWithEmailAndPassword:success")
                            continuation.resume(user?.email.toString())

                        } else {

                            Log.w("", "signInWithEmailAndPassword:failure", it.exception)
                            continuation.resume("")
                        }
                    }
                }
        }
}
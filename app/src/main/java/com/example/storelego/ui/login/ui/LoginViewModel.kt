package com.example.storelego.ui.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storelego.model.Products
import com.example.storelego.repository.ProductsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val productsRepo: ProductsRepository
) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var listProducts : List<Products> = emptyList()

    private val auth: FirebaseAuth = Firebase.auth


    fun singIn(home: () -> Unit) {

        return viewModelScope.run {
            try {
                auth.signInWithEmailAndPassword(_email.value.toString(), _password.value.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            home()
                        } else {
                            Log.d("Fail singIn", "singIn: ${task.result}")
                        }

                    }
            } catch (e: Exception) {
                Log.e("Error ", "singIn: $e ")
            }
        }
    }

    fun createUserEmailPassword(login: () -> Unit) {
        _isLoading.value = true
        if (_isLoading.value == true) {

            auth.createUserWithEmailAndPassword(_email.value.toString(), _password.value.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        login()
                    } else {
                        Log.d("Fail in Created", "createUserEmailPassword: ${task.result}")
                    }
                    _isLoading.value = false
                }
        }
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidPassword(password: String): Boolean = password.length > 5

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()


}
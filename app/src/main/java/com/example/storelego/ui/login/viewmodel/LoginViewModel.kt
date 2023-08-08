package com.example.storelego.ui.login.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storelego.model.Products
import com.example.storelego.repository.LoginRepository
import com.example.storelego.repository.ProductsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showDialog: MutableLiveData<Boolean> = MutableLiveData()
    var showDialog: LiveData<Boolean> = _showDialog


    private val auth: FirebaseAuth = Firebase.auth


    fun signIn(home: () -> Unit) {

        viewModelScope.launch {
            _isLoading.postValue(true)

            val session =
                loginRepository.signIn(_email.value.toString(), _password.value.toString())
            _isLoading.postValue(false)
            if (session.isNotEmpty()) {
                _isLoading.postValue(true)
                home()
                _isLoading.postValue(false)
            } else {
                _isLoading.postValue(false)
                _showDialog.postValue(true)
                delay(4000)
                _showDialog.postValue(false)
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
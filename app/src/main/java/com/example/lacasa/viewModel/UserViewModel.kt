package com.example.lacasa.viewModel

import com.example.lacasa.data.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lacasa.data.User
import kotlinx.coroutines.launch
import java.time.LocalDate

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val users = repository.users

    fun addUser(
        lastName: String,
        name: String,
        email: String,
        password: String,
        signupDate: LocalDate
    ) {
        viewModelScope.launch {
            repository.addUser(lastName, name, email, password, signupDate)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

    fun authenticateUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val isAuthenticated = repository.authenticateUser(email, password)
            if (isAuthenticated) {
                callback(true, null) // Authentification réussie
            } else {
                callback(false, "E-mail ou mot de passe incorrect.")
            }
        }
    }
}

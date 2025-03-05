package com.example.lacasa.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName="user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastName: String,
    val name: String,
    val email: String,
    val password: String,
    val signupDate: LocalDate,
)

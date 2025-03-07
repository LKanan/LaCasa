package com.example.lacasa.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tenant")
data class Tenant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastName: String,
    val name: String,
    val email: String,
    val password: String,
    val signupDate: LocalDate,
)

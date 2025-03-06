package com.example.lacasa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users") // Déclare la table "users" dans Room
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Clé primaire auto-incrémentée
    val name: String, // Nom de l'utilisateur
    val email: String, // Email de l'utilisateur
    val password: String // Mot de passe (⚠️ Pense à le sécuriser !)
)

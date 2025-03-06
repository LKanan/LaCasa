package com.example.lacasa.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName="building")
data class Building(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val address: String?=null,
    val type: String?=null,
    val qtyApart: Int?=null,
    val apartImagePath : String?=null,
    val creationDate: LocalDate,
)
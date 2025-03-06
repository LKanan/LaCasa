package com.example.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
//import com.example.lacasa.data.Building
//import com.example.lacasa.data.Payment
//import com.example.lacasa.data.Tenant
import com.example.lacasa.data.User
//import com.example.lacasa.model.BuildingDao
//import com.example.lacasa.model.PaymentDao
import com.example.lacasa.model.TenantDao
import com.example.lacasa.model.UserDao

//@Database(entities = [Building::class, Payment::class, Tenant::class, User::class], version = 1, exportSchema = false)
@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class) // Permet de stocker LocalDate
abstract class LaCasaDataBase : RoomDatabase() {
//    abstract fun buildingDao(): BuildingDao
//    abstract fun paymentDao(): PaymentDao
//    abstract fun tenantDao(): TenantDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: LaCasaDataBase? = null

        fun getDatabase(context: Context): LaCasaDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LaCasaDataBase::class.java,
                    "lacasa_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
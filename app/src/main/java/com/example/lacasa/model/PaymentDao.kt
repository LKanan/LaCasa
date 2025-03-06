package com.example.lacasa.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment)

    @Query("SELECT * FROM payments")
    suspend fun getAllPayments(): List<Payment>

    @Query("SELECT * FROM payments WHERE tenantId = :tenantId")
    suspend fun getPaymentsByTenant(tenantId: Int): List<Payment>
}

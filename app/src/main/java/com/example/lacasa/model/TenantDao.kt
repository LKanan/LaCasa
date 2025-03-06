package com.example.lacasa.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TenantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTenant(tenant: Tenant)

    @Query("SELECT * FROM tenants")
    suspend fun getAllTenants(): List<Tenant>

    @Query("SELECT * FROM tenants WHERE id = :tenantId")
    suspend fun getTenantById(tenantId: Int): Tenant?

    @Delete
    suspend fun deleteTenant(tenant: Tenant)
}

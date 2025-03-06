package com.example.lacasa.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lacasa.data.Building
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingDao {
    @Query("SELECT * FROM building ORDER BY creationDate ASC")
    fun getAllBuildings(): Flow<List<Building>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuilding(building: Building)

    @Update
    suspend fun updateBuilding(building: Building)

    @Delete
    suspend fun deleteBuilding(building: Building)

    @Query("SELECT * FROM building WHERE id = :id")
    suspend fun getBuildingById(id: Int): Building?
}
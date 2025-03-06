package com.example.lacasa.data
import com.example.lacasa.model.BuildingDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class BuildingRepository(private val buildingDao: BuildingDao) {
    val buildings: Flow<List<Building>> = buildingDao.getAllBuildings()

    suspend fun addBuilding(name: String, address: String?=null, type: String?=null,qtyApart: Int?=null,apartImagePath : String?=null,creationDate: LocalDate) {
        val building = Building(
            name = name,
            address = address,
            type = type,
            qtyApart = qtyApart,
            apartImagePath = apartImagePath,
            creationDate = creationDate
        )
        buildingDao.insertBuilding(building)
    }

    suspend fun updateBuilding(building: Building) {
        buildingDao.updateBuilding(building)
    }

    suspend fun deleteBuilding(building: Building){
        buildingDao.deleteBuilding(building)
    }
}
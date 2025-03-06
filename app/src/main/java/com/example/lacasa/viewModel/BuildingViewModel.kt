package com.example.lacasa.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lacasa.data.Building
import com.example.lacasa.data.BuildingRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class BuildingViewModel(private val repository: BuildingRepository) : ViewModel() {
    val buildings = repository.buildings

    fun addBuilding(
        name: String,
        address: String,
        type: String,
        qtyApart: Int,
        apartImagePath: String,
        creationDate: LocalDate
    ) {
        viewModelScope.launch {
            repository.addBuilding(name, address, type, qtyApart, apartImagePath, creationDate)
        }
    }

    fun updateBuilding(building: Building) {
        viewModelScope.launch {
            repository.updateBuilding(building)
        }
    }

    fun deleteBuilding(building: Building) {
        viewModelScope.launch {
            repository.deleteBuilding(building)
        }
    }
}

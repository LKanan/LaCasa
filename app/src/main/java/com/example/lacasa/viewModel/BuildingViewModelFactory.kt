package com.example.lacasa.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lacasa.data.BuildingRepository
import kotlin.jvm.java

class BuildingViewModelFactory(private val repository: BuildingRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuildingViewModel::class.java)) {
            return BuildingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
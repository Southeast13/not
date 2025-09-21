package com.example.mydomik

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydomik.data.HousePreferences
import kotlinx.coroutines.launch

class HouseViewModel(private val preferences: HousePreferences) : ViewModel() {
    // State for house model
    var wallColor: String = "white"
    var roofColor: String = "red"
    // etc.

    init {
        viewModelScope.launch {
            preferences.wallColor.collect { color ->
                wallColor = color
            }
        }
    }

    fun updateWallColor(color: String) {
        viewModelScope.launch {
            preferences.saveWallColor(color)
        }
    }
}
package com.example.mydomik.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "house_prefs")

class HousePreferences(private val context: Context) {
    companion object {
        val WALL_COLOR_KEY = stringPreferencesKey("wall_color")
        val ROOF_COLOR_KEY = stringPreferencesKey("roof_color")
        // Add more keys for windows, door, etc.
    }

    val wallColor: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[WALL_COLOR_KEY] ?: "white"
    }

    suspend fun saveWallColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[WALL_COLOR_KEY] = color
        }
    }
    
    // Similar for other elements
}
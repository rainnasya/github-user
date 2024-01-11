package com.aca.githubuser2

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map


class SettingsPreferences constructor(private val dataStore: DataStore<Preferences>){

    private val themeKey = booleanPreferencesKey("theme_settings")

    fun getTheme(): kotlinx.coroutines.flow.Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveTheme(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
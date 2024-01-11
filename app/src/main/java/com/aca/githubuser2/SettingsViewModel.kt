package com.aca.githubuser2

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel (private val preferences: SettingsPreferences) : ViewModel() {
    fun getTheme(): LiveData<Boolean> {
        return preferences.getTheme().asLiveData()
    }

    fun saveTheme(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveTheme(isDarkModeActive)
        }
    }



}
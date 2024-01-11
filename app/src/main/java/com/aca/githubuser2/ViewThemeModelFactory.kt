package com.aca.githubuser2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewThemeModelFactory (private val preferences: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
package com.example.birthdayreminder1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdayreminder1.db.BirthdayItem
import com.example.birthdayreminder1.repository.BirthdayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BirthdayViewModel(private val repository: BirthdayRepository):ViewModel() {

    fun insert(birthdayItem: BirthdayItem) = viewModelScope.launch {

        repository.insert(birthdayItem)
    }

    fun delete(birthdayItem: BirthdayItem) = viewModelScope.launch {
        repository.delete(birthdayItem)
    }

    fun getAllBirthdayItems() = repository.getAllBirthdayItems()

}
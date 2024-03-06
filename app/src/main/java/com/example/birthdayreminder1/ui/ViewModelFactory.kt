package com.example.birthdayreminder1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.birthdayreminder1.repository.BirthdayRepository

class ViewModelFactory(private val repository: BirthdayRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BirthdayViewModel(repository) as T
    }

}
package com.example.birthdayreminder1.repository

import com.example.birthdayreminder1.db.BirthdayDatabase
import com.example.birthdayreminder1.db.BirthdayItem

class BirthdayRepository(private val db:BirthdayDatabase) {

    suspend fun insert(birthdayItem: BirthdayItem) = db.birthdayDao().insert(birthdayItem)

    suspend fun delete(birthdayItem: BirthdayItem) = db.birthdayDao().delete(birthdayItem)

    fun getAllBirthdayItems() = db.birthdayDao().gettAllBirthdayItems()


}
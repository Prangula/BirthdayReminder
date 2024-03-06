package com.example.birthdayreminder1.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BirthdatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(birthdayItem: BirthdayItem)

    @Delete
    suspend fun delete(birthdayItem: BirthdayItem)

    @Query("SELECT * FROM birthday_items")
    fun gettAllBirthdayItems():LiveData<List<BirthdayItem>>

}
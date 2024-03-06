package com.example.birthdayreminder1.db

import androidx.core.content.ContextCompat
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.birthdayreminder1.R

@Entity(tableName = "birthday_items")
data class BirthdayItem(

    var title:String = "",
    var to:String = "",
    var image:String = "",
    var time:String = "",
    var color:Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null




)

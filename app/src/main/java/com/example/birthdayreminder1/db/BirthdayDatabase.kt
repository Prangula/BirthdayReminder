package com.example.birthdayreminder1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BirthdayItem::class],
    version = 1
)
abstract class BirthdayDatabase:RoomDatabase() {

    abstract fun birthdayDao():BirthdatDao

    companion object{
        @Volatile
        private var instance: BirthdayDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){

            instance ?: createDatabase(context).also { instance =it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, BirthdayDatabase::class.java,
                "Birthday.db").build()




    }

}
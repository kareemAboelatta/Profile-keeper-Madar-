package com.example.profilekeeper.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.profilekeeper.data.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

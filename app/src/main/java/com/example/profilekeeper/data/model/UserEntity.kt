package com.example.profilekeeper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val username: String,
    val age: Int,
    val jobTitle: String,
)

package com.example.botcrandomizer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val name: String,  // Name is the primary key, no auto generation
    val isCommon: Boolean
)

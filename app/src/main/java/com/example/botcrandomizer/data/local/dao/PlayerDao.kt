package com.example.botcrandomizer.data.local.dao

import androidx.room.*
import com.example.botcrandomizer.data.local.PlayerEntity

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)  // Replace if the name already exists
    suspend fun insertPlayer(player: PlayerEntity)

    @Update
    suspend fun updatePlayer(player: PlayerEntity)

    @Delete
    suspend fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM players")
    fun getAllPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM players WHERE isCommon = 1")
    fun getCommonPlayers(): List<PlayerEntity>
}

package com.example.botcrandomizer.data.repository

import com.example.botcrandomizer.data.local.PlayerEntity
import com.example.botcrandomizer.data.local.dao.PlayerDao

class PlayerRepository(private val playerDao: PlayerDao) {

    fun getAllPlayers() = playerDao.getAllPlayers()

    suspend fun insertPlayer(player: PlayerEntity) {
        playerDao.insertPlayer(player)
    }

    suspend fun updatePlayer(player: PlayerEntity) {
        playerDao.updatePlayer(player)
    }

    suspend fun deletePlayer(player: PlayerEntity) {
        playerDao.deletePlayer(player)
    }
}

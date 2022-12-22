package com.example.clashofbattle.playerEdit

import androidx.lifecycle.ViewModel
import com.example.clashofbattle.api.PlayerAPI
import com.example.clashofbattle.database.PlayerDatabase
import com.example.clashofbattle.models.Player

class PlayerEditViewModel : ViewModel() {

    private val api = PlayerAPI.service

    private val DAO = PlayerDatabase.INSTANCE?.playerDAO()

    fun getPlayerById(id: Long) : Player {
        return DAO!!.getById(id)
    }

    suspend fun updatePlayer(id: String, player: Player){
        DAO?.update(player)
        api.updatePlayer(id, player)
    }
}
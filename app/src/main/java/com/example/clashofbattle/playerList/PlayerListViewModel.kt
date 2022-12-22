package com.example.clashofbattle.playerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.clashofbattle.api.PlayerAPI
import com.example.clashofbattle.database.PlayerDatabase
import com.example.clashofbattle.models.Player
import playerName

class PlayerListViewModel : ViewModel() {

    private var api = PlayerAPI.service

    private var DAO = PlayerDatabase.INSTANCE?.playerDAO()

    val mainPlayer : LiveData<Player>? = DAO?.getMainPlayer(playerName)

    var players : LiveData<List<Player>>? = DAO!!.getAll()

    fun getPlayerById(id: Long) : Player {
        return DAO!!.getById(id)
    }

}
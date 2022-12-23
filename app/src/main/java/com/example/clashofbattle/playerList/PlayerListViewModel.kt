package com.example.clashofbattle.playerList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clashofbattle.api.PlayerAPI
import com.example.clashofbattle.database.PlayerDatabase
import com.example.clashofbattle.models.Player
import com.example.democlashofbattle.utils.toListOfPlayers
import kotlinx.coroutines.launch
import playerName

class PlayerListViewModel : ViewModel() {

    private var api = PlayerAPI.service

    private var DAO = PlayerDatabase.INSTANCE?.playerDAO()

    val mainPlayer : LiveData<Player> = DAO!!.getMainPlayer(playerName)

    var players : LiveData<List<Player>>? = DAO!!.getAll(playerName)

    fun refresh(){
        viewModelScope.launch {
            DAO?.replace(api.getPlayers().toListOfPlayers())
        }
    }

}
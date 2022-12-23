package com.example.clashofbattle.playerEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clashofbattle.api.PlayerAPI
import com.example.clashofbattle.database.PlayerDatabase
import com.example.clashofbattle.models.Capability
import com.example.clashofbattle.models.Player
import kotlinx.coroutines.launch
import playerName

class PlayerEditViewModel : ViewModel() {

    private val api = PlayerAPI.service

    private val DAO = PlayerDatabase.INSTANCE?.playerDAO()

    var player: LiveData<Player> = DAO!!.getMainPlayer(playerName)

    suspend fun getPlayerById(id: Long) : Player? {
        return DAO?.getById(id)
    }

    suspend fun updatePlayer(id: String, player: Player){
        DAO?.update(player)
        api.updatePlayer(id, player)
    }

    fun updateCapability(index: Int, capability: Capability) {
        when(index) {
            0 -> player.value?.capability1 = capability
            1 -> player.value?.capability2 = capability
            2 -> player.value?.capability3 = capability
        }
        viewModelScope.launch { DAO?.update(player.value!!)  }
    }
}
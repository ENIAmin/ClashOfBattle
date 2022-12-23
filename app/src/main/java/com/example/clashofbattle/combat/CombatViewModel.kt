package com.example.clashofbattle.combat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clashofbattle.DAO.PlayerDAO
import com.example.clashofbattle.database.PlayerDatabase
import com.example.clashofbattle.models.Capability
import com.example.clashofbattle.models.Player
import kotlinx.coroutines.launch
import playerName
import kotlin.math.max
import kotlin.math.round

class CombatViewModel : ViewModel() {

    private val DAO: PlayerDAO by lazy { PlayerDatabase.INSTANCE!!.playerDAO() }

    private val battleEngine = BattleEngine(RandomGeneratorImpl())

    val mainPlayer = MutableLiveData<Player>()
    val opponent = MutableLiveData<Player>()

    val mainPlayerInfo = MutableLiveData<PlayerBattleInfo>()
    val opponentInfo = MutableLiveData<PlayerBattleInfo>()

    val roundCount = MutableLiveData(0)

    val lastPlayerResult = MutableLiveData<ActionResult>()
    val lastOpponentResult = MutableLiveData<ActionResult>()

    val winner = MutableLiveData<String>()

    fun init(opponentId: Long) {
        viewModelScope.launch {
            val myPlayer = DAO.getMainPlayerForCombat(playerName)
            val opponentPlayer = DAO.getById(opponentId)
            mainPlayer.value = myPlayer
            opponent.value = opponentPlayer

            mainPlayerInfo.value = PlayerBattleInfo(
                remainingCapabilities = myPlayer.capabilities
            )
            opponentInfo.value = PlayerBattleInfo(
                remainingCapabilities = opponentPlayer.capabilities
            )
        }
    }

    fun attack(capability: Capability? = null) {

        val playerBattleInfo = mainPlayerInfo.value!!
        val opponentBattleInfo = opponentInfo.value!!


        val result = battleEngine.attack(opponentBattleInfo, capability)


        val playerResult = result.first
        val opponentResult = result.second

        mainPlayerInfo.value = updatePlayer(playerBattleInfo, playerResult, opponentResult)
        opponentInfo.value = updatePlayer(opponentBattleInfo, opponentResult, playerResult)

        println("Opponent Info ${opponentInfo.value!!.pv}")

        lastPlayerResult.value = playerResult
        lastOpponentResult.value = opponentResult

        roundCount.value = roundCount.value!! + 1

        if(mainPlayerInfo.value!!.pv <= 0) {
            winner.value = opponent.value!!.name

        } else if(opponentInfo.value!!.pv <= 0) {
            winner.value = mainPlayer.value!!.name
        }
    }

    private fun updatePlayer(
        player: PlayerBattleInfo,
        playerResult: ActionResult,
        opponentResult: ActionResult
    ): PlayerBattleInfo {
        var newPlayer = player

        val realDamage = max(0, opponentResult.damage - playerResult.defense)
        val heal = playerResult.heal

        playerResult.usedCapability?.let {
            val newCapabilitiesList = player.remainingCapabilities.toMutableList()
            newCapabilitiesList.remove(it)
            newPlayer = newPlayer.copy(remainingCapabilities = newCapabilitiesList)
        }

        val modifiedPv = newPlayer.pv - realDamage + heal
        val pv = Integer.min(50, Integer.max(0, modifiedPv))

        return newPlayer.copy(pv = pv)
    }

}
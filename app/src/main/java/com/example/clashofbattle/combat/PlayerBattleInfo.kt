package com.example.clashofbattle.combat

import com.example.clashofbattle.models.Capability

data class PlayerBattleInfo(
    val remainingCapabilities: List<Capability>,
    val pv: Int = 50
)
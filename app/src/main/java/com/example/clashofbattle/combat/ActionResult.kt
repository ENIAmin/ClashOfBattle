package com.example.clashofbattle.combat

import com.example.clashofbattle.models.Capability

data class ActionResult(
    val damage: Int = 0,
    val defense: Int = 0,
    val heal: Int = 0,
    val usedCapability: Capability? = null,
)
package com.example.clashofbattle.combat

import com.example.clashofbattle.models.Capability
import com.example.clashofbattle.models.CapabilityType

class BattleEngine(private val randomGenerator: RandomGenerator) {

    fun attack(
        opponent: PlayerBattleInfo,
        capability: Capability?
    ): Pair<ActionResult, ActionResult> {

        val playerResult = if(capability == null) {
            doSimpleAttack()
        } else {
            useCapability(capability)
        }

        val opponentResult = opponentAttack(opponent)

        return playerResult to opponentResult
    }

    private fun opponentAttack(opponent: PlayerBattleInfo): ActionResult {
        val attackType = randomGenerator.getRandomAction(opponent.remainingCapabilities.size)

        return when (attackType) {
            1 -> doSimpleAttack()
            2 -> useCapability(opponent.remainingCapabilities[0])
            3 -> useCapability(opponent.remainingCapabilities[1])
            4 -> useCapability(opponent.remainingCapabilities[2])
            else -> throw IllegalStateException("Acceptable range here is (1..${opponent.remainingCapabilities.size})")
        }
    }

    private fun useCapability(capability: Capability): ActionResult {
        val value = getCapabilityValue(capability)

        return when (capability.type) {
            CapabilityType.ATTACK -> doAttackCapability(value).copy(usedCapability = capability)
            CapabilityType.DEFENSE -> doDefenseCapability(value).copy(usedCapability = capability)
            CapabilityType.HEAL -> doHealCapability(value).copy(usedCapability = capability)
        }
    }

    private fun getCapabilityValue(capability: Capability) = when (capability) {
        Capability.RISKY_ATTACK,
        Capability.RISKY_PARRY,
        Capability.RISKY_HEAL -> randomGenerator.getRiskyD6()

        Capability.DOUBLE_ATTACK,
        Capability.DOUBLE_PARRY,
        Capability.DOUBLE_HEAL -> randomGenerator.getDoubleD6()

        Capability.PRECISE_ATTACK,
        Capability.PRECISE_PARRY,
        Capability.PRECISE_HEAL -> randomGenerator.getPreciseD6()
    }

    private fun doSimpleAttack() = ActionResult(damage = randomGenerator.getD6())

    private fun doAttackCapability(value: Int) = ActionResult(damage = value)

    private fun doDefenseCapability(value: Int) = ActionResult(
        damage = randomGenerator.getD6(),
        defense = value
    )

    private fun doHealCapability(value: Int) = ActionResult(heal = value)
}
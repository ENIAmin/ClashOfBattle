package com.example.clashofbattle.combat

class RandomGeneratorImpl : RandomGenerator {

    override fun getD6() = (1..6).random()

    override fun getRiskyD6() = getD6() * 2

    override fun getDoubleD6() = getD6() + getD6()

    override fun getPreciseD6() = getD6() + 3

    override fun getRandomAction(remainingCapabilitiesCount: Int) =
        (1..(remainingCapabilitiesCount + 1)).random()
}
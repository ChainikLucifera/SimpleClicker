package com.example.simpleclicker

import com.example.simpleclicker.models.Upgrade

object Upgrades {
    val POWER_UPGRADES = listOf<Upgrade>(
        Upgrade(10, 1, false, "Power +1"),
        Upgrade(100, 100, false, "Power +100"),
        Upgrade(1000, 500, false, "Power +500")
    )
}
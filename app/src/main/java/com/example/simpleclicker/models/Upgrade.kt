package com.example.simpleclicker.models
import com.example.simpleclicker.Constants
import kotlin.math.roundToInt

class Upgrade(private val baseCost: Int,
              val upgradePower: Int,
              val isPassive: Boolean,
              val name: String) {

    var lvl = 1
    var currentCost = baseCost

    fun lvlUp(){
        currentCost = (baseCost * Constants.LVL_MULTIPLIER * lvl).roundToInt()
        lvl += 1
    }
}
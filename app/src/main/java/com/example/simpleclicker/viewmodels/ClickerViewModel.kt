package com.example.simpleclicker.viewmodels

import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleclicker.Upgrades
import com.example.simpleclicker.models.Upgrade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private val powerUpgrades = Upgrades.POWER_UPGRADES

data class ClickerState(
    var clicks: Int = 0,
    var powerOfClick: Int = 1,
    var passiveIncome: Int = 0,
)

data class CurrentUpgradeState(
    var currentCost: MutableList<Int> = mutableListOf<Int>(),
    var currentLvl: MutableList<Int> = mutableListOf<Int>()
)

class ClickerViewModel() : ViewModel() {
    private val _clickerState = MutableStateFlow(ClickerState())
    val clickerState = _clickerState.asStateFlow()

    private val _upgradeState = MutableStateFlow(value = CurrentUpgradeState())
    val upgradeState = _upgradeState.asStateFlow()

    private val _showToastEvent = MutableLiveData<String>() // Or SingleLiveEvent<String>()
    val showToastEvent: LiveData<String> = _showToastEvent

    init {
        fillUpgradeState()
    }

    fun addClick() {
        _clickerState.update { current ->
            current.copy(
                clicks = current.clicks + current.powerOfClick
            )
        }
    }

    fun getPowerUpgradesNumber(): Int = powerUpgrades.size

    fun getPowerUpgradesName(): MutableList<String> {
        val upgradesName = mutableListOf<String>()
        for (upgrade in powerUpgrades) {
            upgradesName.add(upgrade.name)
        }
        return upgradesName
    }

    fun buttonPressed(index: Int) {
        Log.d("TEST", "Button $index pressed!")
        if(addPower(powerUpgrades[index]))
            updateUpgradeState(index, powerUpgrades[index])

    }

    private fun addPower(upgrade: Upgrade): Boolean {
        if (clickerState.value.clicks >= upgrade.currentCost) {
            _clickerState.update { current ->
                current.copy(
                    clicks = current.clicks - upgrade.currentCost,
                    powerOfClick = current.powerOfClick + upgrade.upgradePower
                )
            }
            upgrade.lvlUp()
            return true
        }
        else {
            sendTextError("Not enough CLICKS!")
            return false
        }
    }

    private fun updateUpgradeState(index: Int, upgrade: Upgrade){
        _upgradeState.update { currentUpgradeState ->

            val costTemp = currentUpgradeState.currentCost.toMutableList()
            val lvlTemp = currentUpgradeState.currentLvl.toMutableList()

            costTemp[index] = upgrade.currentCost
            lvlTemp[index] = upgrade.lvl

            currentUpgradeState.copy(
                currentCost = costTemp,
                currentLvl = lvlTemp
            )
        }
        Log.d("TEST", "UpgradeState Done! => " +
                "Button $index with LVL ${_upgradeState.value.currentLvl[index]}" +
                " costs ${_upgradeState.value.currentCost[index]}")

    }

    private fun sendTextError(message: String) {
        _showToastEvent.value = message
    }

    private fun fillUpgradeState() {
        val costTemp = mutableListOf<Int>()
        val lvlTemp = mutableListOf<Int>()

        for (upgrade in powerUpgrades) {
            costTemp.add(upgrade.currentCost)
            lvlTemp.add(upgrade.lvl)
        }

        _upgradeState.update { currentUpgradeState ->
            currentUpgradeState.copy(
                currentCost = costTemp,
                currentLvl = lvlTemp
            )
        }
    }
}
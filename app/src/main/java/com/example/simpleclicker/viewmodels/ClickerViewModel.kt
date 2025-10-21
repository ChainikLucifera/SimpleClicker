package com.example.simpleclicker.viewmodels

import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.ViewModel
import com.example.simpleclicker.models.Upgrade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class ClickerState(
    var clicks: Int = 0,
    var powerOfClick: Int = 1,
    var passiveIncome: Int = 0,
)

class ClickerViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ClickerState())
    val state = _state.asStateFlow()

    private val upgrade = Upgrade(10, 1, false)

    fun addClick() {
        _state.update { current ->
            current.copy(
                clicks = current.clicks + current.powerOfClick
            )
        }
    }

    fun addPower() {
        if(state.value.clicks >= upgrade.currentCost){
            _state.update { current ->
                current.copy(
                    clicks = current.clicks - upgrade.currentCost,
                    powerOfClick = current.powerOfClick + upgrade.upgradePower
                )
            }
            upgrade.lvlUp()
        }
    }
}
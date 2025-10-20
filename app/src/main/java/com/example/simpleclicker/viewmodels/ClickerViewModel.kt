package com.example.simpleclicker.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class ClickerStats(
    var clicks: Int = 0,
    var powerOfClick: Int = 1,
    var passiveIncome: Int = 0,
)

class ClickerViewModel() : ViewModel() {
    private val _stats = MutableStateFlow(ClickerStats())
    val stats = _stats.asStateFlow()


    fun addClick(){
        Log.d("TEST", "${stats.value.clicks}")
        Log.d("TEST", "Click!")

        _stats.update { current ->
            current.copy(
                clicks = current.clicks + current.powerOfClick
            )
        }

        Log.d("TEST", "${stats.value.clicks}")
    }
}
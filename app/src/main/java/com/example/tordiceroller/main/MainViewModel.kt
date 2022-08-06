package com.example.tordiceroller.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _numberOfDices = MutableLiveData<Int>()
    private val numberOfDices: LiveData<Int>
        get() = _numberOfDices

    private var _numberOfDicesText = MutableLiveData<String>()
    val numberOfDicesText: LiveData<String>
        get() = _numberOfDicesText

    private var _resultText = MutableLiveData<String>()
    val resultText: LiveData<String>
        get() = _resultText

    private var _canRoll = MutableLiveData<Boolean>()
    val canRoll: LiveData<Boolean>
        get() = _canRoll

    init {
        _canRoll.value = true
        _numberOfDices.value = 1
        _numberOfDicesText.value = "1d"
    }

    fun plusOneDice() {
        if (canRoll.value!!) {
            _numberOfDices.value = numberOfDices.value!! + 1
            updateNumberOfDicesText()
        }
    }

    fun minusOneDice() {
        if (canRoll.value!!) {
            if (numberOfDices.value!! > 1) {
                _numberOfDices.value = numberOfDices.value!! - 1
                updateNumberOfDicesText()
            }
        }
    }

    private fun updateNumberOfDicesText() {
        _numberOfDicesText.value = numberOfDices.value.toString() + "d"
    }

    fun roll(numberOfSides: Int) {
        val dices = numberOfDices.value!!
        var result = 0
        repeat(dices) {
            result += (1..numberOfSides).random()
        }

        _resultText.value = "${dices}d$numberOfSides: $result"
        _canRoll.value = false
    }

    fun popupWindowClosed() {
        _canRoll.value = true
    }
}
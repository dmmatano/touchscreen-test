package com.example.displaytestgertec.ui.touchtest.viewmodel

import android.graphics.Rect
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TouchTestViewModel: ViewModel() {
    private val _touchedSquaresIndexes = MutableLiveData<MutableList<Int>>()
    val touchedSquaresIndexes: LiveData<MutableList<Int>> get() = _touchedSquaresIndexes

    private val _timeout = MutableLiveData<Boolean>()
    val timeout: LiveData<Boolean> get() = _timeout

    private var countDownTimer: CountDownTimer

    init {
        _touchedSquaresIndexes.value = (0..31).toMutableList()

        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                _timeout.postValue(true)
            }
        }
    }

    fun startCountDown(){
        countDownTimer.start()
    }

    fun stopCountDown(){
        countDownTimer.cancel()
    }

    fun verifySquareCoordinate(gridRect: Rect, squareRect: Rect,x: Float, y: Float, index: Int): Boolean {

        val squareX = squareRect.left - gridRect.left
        val squareY = squareRect.top - gridRect.top

        return if(x >= squareX && x <= squareX + squareRect.width() &&
            y >= squareY && y <= squareY + squareRect.height()) {
            _touchedSquaresIndexes.value?.remove(index)
            _touchedSquaresIndexes.postValue(_touchedSquaresIndexes.value)
            true
        } else{
            false
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopCountDown()
    }

}
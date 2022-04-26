package com.kgp.myaccount.ui.editornew

import androidx.lifecycle.MutableLiveData
import com.kgp.myaccount.ui.baseclass.BaseViewModel
import java.time.LocalDateTime

class EditHistoryViewModel : BaseViewModel() {

    val historyMode get() = _historyMode
    private val _historyMode = MutableLiveData(HistoryMode.EXPENSE)

    val currentDateTime get() = _currentSetLocalDateTime
    private val _currentSetLocalDateTime = MutableLiveData(LocalDateTime.now())

    fun changeMode(historyMode: HistoryMode) {
        _historyMode.value = historyMode
    }

    fun changeDate(year: Int, month: Int, day: Int) {
        val prev = _currentSetLocalDateTime.value

        prev?.also {
            val newTime = LocalDateTime.of(year, month, day, it.hour, it.minute)
            _currentSetLocalDateTime.value = newTime
        }
    }

    fun changeTime(hour: Int, minute: Int) {
        val prev = _currentSetLocalDateTime.value

        prev?.also {
            val newTime = LocalDateTime.of(it.year, it.month, it.dayOfMonth, hour, minute)
            _currentSetLocalDateTime.value = newTime
        }
    }
}
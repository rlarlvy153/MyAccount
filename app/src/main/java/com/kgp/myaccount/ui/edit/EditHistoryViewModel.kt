package com.kgp.myaccount.ui.edit

import androidx.lifecycle.MutableLiveData
import com.kgp.myaccount.ui.baseclass.BaseViewModel

class EditHistoryViewModel : BaseViewModel() {

    val historyMode get() = _historyMode
    private val _historyMode = MutableLiveData(HistoryMode.EXPENSE)

    fun changeMode(historyMode: HistoryMode) {
        _historyMode.value = historyMode
    }
}
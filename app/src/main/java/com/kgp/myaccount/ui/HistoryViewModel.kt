package com.kgp.myaccount.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kgp.myaccount.db.HistoryRepository
import com.kgp.myaccount.ui.baseclass.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HistoryViewModel : BaseViewModel() {
    private val rep: HistoryRepository by inject()

    fun getAllHistories(): Flow<List<HistoryItem>> {
        return rep.getAllHistories().map { histories ->
            val historyItems = mutableListOf<HistoryItem>()

            histories.forEach {
                val historyItem = HistoryItem(it.moneyLong, it.detail, it.shouldIgnore)
                historyItems.add(historyItem)
            }

            historyItems
        }
    }

    fun insert(money: Long, detail: String, category: Int) {
        viewModelScope.launch {
            rep.insertHistory(money, detail, category)
        }
    }
}
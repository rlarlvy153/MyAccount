package com.kgp.myaccount.ui

import androidx.lifecycle.viewModelScope
import com.kgp.myaccount.db.HistoryRepository
import com.kgp.myaccount.ui.baseclass.BaseViewModel
import com.kgp.myaccount.ui.edit.HistoryMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import java.time.LocalDateTime

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

    fun insert(_money: Long, detail: String, mode: HistoryMode, dateTime: LocalDateTime) {
        viewModelScope.launch {
            val money = when(mode) {
                HistoryMode.IGNORE, HistoryMode.INCOME -> _money
                HistoryMode.EXPENSE -> {
                    -_money
                }
            }
            val shouldIgnore = mode == HistoryMode.IGNORE

            rep.insertHistory(money, detail, shouldIgnore, dateTime, 1)
        }
    }
}
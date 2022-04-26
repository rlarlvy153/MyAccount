package com.kgp.myaccount.ui

import androidx.lifecycle.viewModelScope
import com.kgp.myaccount.db.HistoryRepository
import com.kgp.myaccount.db.entity.History
import com.kgp.myaccount.ui.baseclass.BaseViewModel
import com.kgp.myaccount.ui.editornew.HistoryMode
import com.kgp.myaccount.ui.historytype.DayItem
import com.kgp.myaccount.ui.historytype.HistoryBaseItem
import com.kgp.myaccount.ui.historytype.HistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import java.time.LocalDate
import java.time.LocalDateTime

class HistoryViewModel : BaseViewModel() {
    private val rep: HistoryRepository by inject()

    fun getAllHistories(): Flow<List<HistoryBaseItem>> {
        return rep.getAllHistories().map { histories ->
            val historyResult = mutableListOf<HistoryBaseItem>()

            var previousHistory = History(-1, localDateTime = LocalDateTime.MIN)
            var lastSection = DayItem(LocalDate.of(0, 1, 1), 0)

            for (history in histories) {
                if (previousHistory.localDateTime.dayOfMonth != history.localDateTime.dayOfMonth) {
                    val year = history.localDateTime.year
                    val month = history.localDateTime.monthValue
                    val day = history.localDateTime.dayOfMonth

                    lastSection = DayItem(LocalDate.of(year, month, day), 0)
                    historyResult.add(lastSection)
                }

                lastSection.also {
                    if(history.shouldIgnore.not()){
                        it.sumOfDay += history.moneyLong
                    }
                }

                val historyItem = HistoryItem(
                    history.moneyLong,
                    history.detail,
                    history.localDateTime,
                    history.shouldIgnore
                )
                historyResult.add(historyItem)

                previousHistory = history
            }

            historyResult
        }
    }

    fun insert(_money: Long, detail: String, mode: HistoryMode, dateTime: LocalDateTime) {
        viewModelScope.launch {
            val money = when (mode) {
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
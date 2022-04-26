package com.kgp.myaccount.db

import com.kgp.myaccount.db.entity.History
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime

class HistoryRepository : KoinComponent {
    private val historyDao: HistoryDao by inject()

    suspend fun insertHistory(money: Long, detail: String, shouldIgnore: Boolean, date: LocalDateTime, category: Int): History {
        val history = History(
            0,
            moneyLong = money,
            detail = detail,
            category = category,
            shouldIgnore = shouldIgnore,
            localDateTime = date
        )
        val insertedId = historyDao.insertHistory(history)
        return history.also { it.id = insertedId }
    }

    fun getAllHistories(): Flow<List<History>> = historyDao.getAllHistory()
}
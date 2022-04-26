package com.kgp.myaccount.ui.historytype

import com.kgp.myaccount.ui.historytype.HistoryBaseItem
import java.time.LocalDateTime

data class HistoryItem(
    val money: Long = 0,
    val detail: String = "",
    val localDateTime: LocalDateTime = LocalDateTime.now(),
    val shouldIgnore: Boolean = false
) : HistoryBaseItem()

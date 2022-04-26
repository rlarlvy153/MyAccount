package com.kgp.myaccount.ui.historytype

import java.time.LocalDate

class DayItem(
    val day: LocalDate,
    var sumOfDay: Long
) : HistoryBaseItem()
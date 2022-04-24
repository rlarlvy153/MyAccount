package com.kgp.myaccount.ui

data class HistoryItem(
    val money: Long,
    val detail: String,
    val shouldIgnore: Boolean
)
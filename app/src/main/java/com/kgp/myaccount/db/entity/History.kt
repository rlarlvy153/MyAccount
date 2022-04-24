package com.kgp.myaccount.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "History")
data class History(
    @PrimaryKey(autoGenerate = true)
    @NonNull var id: Long,
    val moneyLong: Long = 0,
    val detail: String = "",
    val category: Int = 0,
    val shouldIgnore: Boolean = false,
    val timestamp: LocalDateTime
)
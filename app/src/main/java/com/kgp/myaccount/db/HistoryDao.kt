package com.kgp.myaccount.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kgp.myaccount.db.entity.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    suspend fun insertHistory(history: History): Long

    @Query("SELECT * FROM History")
    fun getAllHistory(): Flow<List<History>>
}
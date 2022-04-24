package com.kgp.myaccount.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kgp.myaccount.db.entity.History

@Database(
    entities = [
        History::class
    ],
    version = AppDatabase.VERSION
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun historyDao(): HistoryDao

}
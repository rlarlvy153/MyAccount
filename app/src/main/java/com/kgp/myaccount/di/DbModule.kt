package com.kgp.myaccount.di


import androidx.room.Room
import com.kgp.myaccount.MyApp
import com.kgp.myaccount.db.AppDatabase
import com.kgp.myaccount.db.HistoryRepository
import org.koin.dsl.module

val dbModule = module {
    val db = Room.databaseBuilder(MyApp.appContext, AppDatabase::class.java, "history")
        .build()
    single { db }
    single { db.historyDao() }
    single { HistoryRepository() }
}
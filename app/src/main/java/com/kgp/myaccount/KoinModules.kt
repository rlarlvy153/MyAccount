package com.kgp.myaccount

import com.kgp.myaccount.di.dbModule
import com.kgp.myaccount.di.viewModelModule

val koinAppModules = listOf(dbModule, viewModelModule)
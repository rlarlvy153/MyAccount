package com.kgp.myaccount

import android.app.Application
import android.content.Context
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApp : Application(){
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this

        initTimber()

        startKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun startKoin() {
        startKoin {
            modules(koinAppModules)
        }
    }
}
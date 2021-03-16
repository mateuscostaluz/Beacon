package com.mateuscostaluz.presensor.ui.application

import android.app.Application
import com.mateuscostaluz.presensor.modules.factory.getFactoryModules
import com.mateuscostaluz.presensor.modules.single.getSingleModules
import com.mateuscostaluz.presensor.modules.viewmodel.getViewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

open class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        var modules = getAllModules()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(listOf(modules))
        }
    }

    private fun getAllModules(): Module {
        return module(override = true) {
            getSingleModules(this@Application)
            getFactoryModules(this@Application)
            getViewModelModules(this@Application)
        }
    }
}
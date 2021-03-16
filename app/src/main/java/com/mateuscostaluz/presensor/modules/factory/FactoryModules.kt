package com.mateuscostaluz.presensor.modules.factory

import android.content.Context

import org.koin.core.module.Module


fun Module.getFactoryModules(context: Context) {
    factory { com.mateuscostaluz.presensor.services.RetrofitService(get()) }
    factory { com.mateuscostaluz.presensor.services.dialog.DialogCreation() }
}
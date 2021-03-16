package com.mateuscostaluz.presensor.modules.single

import android.content.Context
import org.koin.core.module.Module

fun Module.getSingleModules(context: Context) {
    single { com.mateuscostaluz.presensor.ui.adapter.BeaconAdapter(context,get()) }
    single { com.mateuscostaluz.presensor.services.retrofit.RetrofitRef(get()) }
    single { com.mateuscostaluz.presensor.global.Global() }
}
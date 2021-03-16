package com.mateuscostaluz.presensor.ui.fragment

import com.mateuscostaluz.presensor.models.BeaconReceived

interface BeaconInterf {
    fun changeView(beacon:BeaconReceived)
}
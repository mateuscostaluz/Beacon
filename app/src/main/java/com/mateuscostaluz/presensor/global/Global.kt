package com.mateuscostaluz.presensor.global

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuscostaluz.presensor.models.UserReceived

class Global {

    var user: LiveData<UserReceived> = MutableLiveData(null)
    var jwt: LiveData<String> = MutableLiveData(null)
}
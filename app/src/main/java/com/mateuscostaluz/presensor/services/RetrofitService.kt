package com.mateuscostaluz.presensor.services

import android.util.Log
import com.google.gson.JsonObject
import com.mateuscostaluz.presensor.models.*
import com.mateuscostaluz.presensor.services.interfaces.RetrofitInterface
import com.mateuscostaluz.presensor.services.retrofit.RetrofitRef
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

open class RetrofitService(
    retrofitRef: RetrofitRef
) {

    private var retrofitInt: RetrofitInterface = retrofitRef.getRetrofitInterface()

    fun getCurrentTimeBeaconUUID(beaconUUID: String): Observable<Request<BeaconReceived>> {
        return retrofitInt.getHorarioAtualBeaconUUID(beaconUUID)
    }

    fun login(user: User): Observable<Request<String>> {
        return retrofitInt.login(user)
    }
    fun register(user: UserReceived): Observable<Request<Object>> {
        Log.i("User : ",user.toString())
        return retrofitInt.cadastrar(user)
    }

    fun confirmPresence(presence: Presence): Observable<Request<Object>> {
        return retrofitInt.confirmPresence(presence)
    }

}
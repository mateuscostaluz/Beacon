package com.mateuscostaluz.presensor.services.interfaces

import com.google.gson.JsonObject
import com.mateuscostaluz.presensor.models.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitInterface {
    @GET("horario/atual")
    fun getHorarioAtualBeaconUUID(@Query("beacon") beacon_uuid: String): Observable<Request<BeaconReceived>>

    @POST("aluno/login")
    @Headers("Content-Type: application/json")
    fun login(
        @Body user: User
    ): Observable<Request<String>>

    @POST("/aluno/cadastro")
    fun cadastrar(
        @Body userReceived: UserReceived
    ): Observable<Request<Object>>

    @POST("/presenca/")
    fun confirmPresence(
        @Body presence: Presence
    ): Observable<Request<Object>>

}
package com.mateuscostaluz.presensor.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.mateuscostaluz.presensor.global.Global
import com.auth0.android.jwt.Claim
import com.auth0.android.jwt.JWT
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mateuscostaluz.presensor.R
import com.mateuscostaluz.presensor.models.BeaconReceived
import com.mateuscostaluz.presensor.models.Presence
import com.mateuscostaluz.presensor.services.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject


class BeaconDescription : Fragment(R.layout.beacon_description) {

    val args: BeaconDescriptionArgs by navArgs()
    private val retrofitService by inject<RetrofitService>()
    private val global by inject<Global>()

    private lateinit var id: TextView
    private lateinit var subjectInitials: TextView
    private lateinit var subjectName: TextView
    private lateinit var classRoomNumber: TextView
    private lateinit var classRoomUUIDBeacon: TextView
    private lateinit var dayWeekID: TextView
    private lateinit var dayWeekDay: TextView
    private lateinit var initialHour: TextView
    private lateinit var endHour: TextView
    private lateinit var confirmButtom: FloatingActionButton
    private lateinit var exitButton: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var beacon = args.beacon

        initVariables(view)

        setVariables(beacon)

        setListeners(beacon, view)
    }

    private fun setListeners(
        beacon: BeaconReceived,
        view: View
    ) {
        exitButton.setOnClickListener {
            (global.jwt as MutableLiveData<String>).value = null
            Navigation.findNavController(requireView()).navigate(R.id.login)
        }

        confirmButtom.setOnClickListener {
            val parsedJWT = JWT(global.jwt.value!!.toString())
            val subscriptionMetaData: Claim = parsedJWT.getClaim("ra_aluno")
            val parsedValue = subscriptionMetaData.asString()
            val presence = Presence(parsedValue!!, beacon.id)

            retrofitService.confirmPresence(presence)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if(it.status != 200){
                        Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG)
                            .show()
                        Navigation.findNavController(requireView()).popBackStack()
                    }
                }, {
                    Log.e("Error", it.fillInStackTrace().toString())
                    Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG).show()
                })
        }
    }

    private fun setVariables(beacon: BeaconReceived) {
        id.text = beacon.id
        subjectInitials.text = beacon.subject.initials
        subjectName.text = beacon.subject.name
        classRoomUUIDBeacon.text = beacon.classRoom.uuidBeacon
        classRoomNumber.text = beacon.classRoom.number
        dayWeekID.text = beacon.dayWeek.id.toString()
        dayWeekDay.text = beacon.dayWeek.day
        initialHour.text = beacon.initialHour
        endHour.text = beacon.endHour
    }

    private fun initVariables(view: View) {
        id = view.findViewById(R.id.beacon_description_id)
        subjectInitials = view.findViewById(R.id.beacon_description_disciplina_sigla)
        subjectName = view.findViewById(R.id.beacon_description_disciplina_nome)
        classRoomNumber = view.findViewById(R.id.beacon_description_sala_numero)
        classRoomUUIDBeacon = view.findViewById(R.id.beacon_description_sala_uuid)
        dayWeekID = view.findViewById(R.id.beacon_description_diasemana_id)
        dayWeekDay = view.findViewById(R.id.beacon_description_diasemana_dia)
        initialHour = view.findViewById(R.id.beacon_description_horarioinicio)
        endHour = view.findViewById(R.id.beacon_description_horariofim)
        confirmButtom = view.findViewById(R.id.beacon_description_confirm)
        exitButton = view.findViewById(R.id.beacon_description_exit)
    }
}
package com.mateuscostaluz.presensor.ui.adapter

import android.util.Log
import android.view.View
import com.google.android.material.textview.MaterialTextView
import com.mateuscostaluz.presensor.R
import com.mateuscostaluz.presensor.models.Beacon
import com.mateuscostaluz.presensor.services.RetrofitService
import com.mateuscostaluz.presensor.ui.fragment.BeaconInterf
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BeaconHolder(
    itemView: View,
    var retrofitService: RetrofitService,
    private var beaconInterf: BeaconInterf
) :
    BaseViewHolder<Beacon>(itemView) {

    private var uuid = itemView.findViewById<MaterialTextView>(R.id.beacon_item_uuid)
    private var mac = itemView.findViewById<MaterialTextView>(R.id.beacon_item_mac)

    private lateinit var beacon:Beacon
    init {
        itemView.setOnClickListener {
            Log.i("Beacon sent : ",beacon.uuid.toString())
            retrofitService.getCurrentTimeBeaconUUID(beacon.uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("BeaconReceived : ",it.toString())
                    beaconInterf.changeView(it.data)
                },{
                    Log.e("Error : ",it.fillInStackTrace().toString())
                })

        }
    }

    override fun bind(beacon: Beacon) {
        this.beacon = beacon
        uuid.text = beacon.uuid
        mac.text = beacon.mac
    }
}
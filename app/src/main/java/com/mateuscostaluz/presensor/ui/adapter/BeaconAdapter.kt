package com.mateuscostaluz.presensor.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuscostaluz.presensor.R
import com.mateuscostaluz.presensor.models.Beacon
import com.mateuscostaluz.presensor.services.RetrofitService
import com.mateuscostaluz.presensor.ui.fragment.BeaconInterf

open class BeaconAdapter(
    var context: Context,
    var retrofitService: RetrofitService
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    lateinit var beaconInterf: BeaconInterf

    override fun getItemCount(): Int {
        Log.i("Size", beaconList.size.toString())
        return beaconList.size
    }

    fun addList(beaconListReceived: List<Beacon>) {
        Log.i("Adapter", beaconListReceived.toString())
        beaconList.clear()
        beaconList.addAll(beaconListReceived)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        var beaconItem = beaconList.get(position)
        when (holder) {
            is BeaconHolder -> (holder as BeaconHolder?)!!.bind(
                beaconItem
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.beacon_item, parent, false)
        return BeaconHolder(view, retrofitService, beaconInterf)
    }

    companion object {
        private var beaconList =
            arrayListOf<Beacon>()
    }
}
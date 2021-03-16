package com.mateuscostaluz.presensor.ui.fragment

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mateuscostaluz.presensor.global.Global
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mateuscostaluz.presensor.R
import com.mateuscostaluz.presensor.models.Beacon
import com.mateuscostaluz.presensor.models.BeaconReceived
import com.mateuscostaluz.presensor.services.dialog.DialogCreation
import com.mateuscostaluz.presensor.ui.adapter.BeaconAdapter
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region
import org.koin.android.ext.android.inject
import java.util.stream.Collectors


class BeaconList : Fragment(R.layout.beacon_list),
    BeaconInterf, BeaconConsumer {

    private var beaconManager: BeaconManager? = null
    private val TAG = "BeaconList"

    private val adapter by inject<BeaconAdapter>()
    private val dialogCreation by inject<DialogCreation>()
    private val global by inject<Global>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton: FloatingActionButton
    private lateinit var cancelButton: FloatingActionButton
    private lateinit var exitButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBeaconManager()
    }

    private fun setBeaconManager() {
        beaconManager =
            BeaconManager.getInstanceForApplication(requireActivity().applicationContext)
        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialogCreation.build(requireContext())
        initVariables(view)
        setVariables()
        setListeners()
    }

    private fun setListeners() {
        searchButton.setOnClickListener {
            val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val isEnabled: Boolean = bluetoothAdapter.isEnabled
            if (!isEnabled) {
                Toast.makeText(requireContext(), "Ativar o bluetooth primeiro!", Toast.LENGTH_LONG)
                    .show()
            } else if (isEnabled) {
                bind()
                cancelButton.visibility = View.VISIBLE
            }

        }
        cancelButton.setOnClickListener {
            unbind()
            cancelButton.visibility = View.GONE
        }

        exitButton.setOnClickListener {
            (global.jwt as MutableLiveData<String>).value = null
            Navigation.findNavController(requireView()).navigate(R.id.login)
        }
    }

    private fun setVariables() {
        recyclerView.adapter = adapter
        adapter.beaconInterf = this
    }

    private fun initVariables(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.beacon_list_recyclerview)
        searchButton = view.findViewById<FloatingActionButton>(R.id.beacon_list_search)
        cancelButton = view.findViewById<FloatingActionButton>(R.id.beacon_list_cancel)
        exitButton = view.findViewById<ImageView>(R.id.beacon_list_exit)
    }


    fun bind() {
        beaconManager = BeaconManager.getInstanceForApplication(requireView().context)
        beaconManager!!.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
        beaconManager!!.bind(this@BeaconList)
    }

    fun unbind() {
        beaconManager!!.unbind(this@BeaconList)
        beaconManager!!.removeAllRangeNotifiers()
        beaconManager!!.removeAllMonitorNotifiers()
    }


    override fun onDestroy() {
        super.onDestroy()
        beaconManager!!.unbind(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBeaconServiceConnect() {
        beaconManager?.removeAllRangeNotifiers()
        beaconManager?.backgroundMode = true
        beaconManager?.backgroundScanPeriod = 1100
        beaconManager?.foregroundScanPeriod = 5000
        beaconManager?.addRangeNotifier { beacons, _ ->
            if (beacons!!.size > 0) {
                var beaconNewList =
                    beacons.stream().map { it -> Beacon(it.id1.toString(), it.bluetoothAddress) }
                        .collect(Collectors.toList())

                Log.i("Found ", beaconNewList.toString())
                adapter.addList(beaconNewList)
            }
        }
        try {
            beaconManager!!.startRangingBeaconsInRegion(
                Region(
                    "myRangingUniqueId",
                    null,
                    null,
                    null
                )
            )
        } catch (e: RemoteException) {
            Toast.makeText(context, e.stackTrace.toString(), Toast.LENGTH_LONG).show()
            Log.d("BeaconList : ", e.stackTrace.toString())
        }
    }

    override fun getApplicationContext(): Context? {
        return requireActivity().applicationContext
    }

    override fun unbindService(serviceConnection: ServiceConnection?) {
        requireActivity().unbindService(serviceConnection!!)
    }

    override fun bindService(
        intent: Intent?,
        serviceConnection: ServiceConnection?,
        i: Int
    ): Boolean {
        return requireActivity().bindService(intent, serviceConnection!!, i)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        beaconManager!!.unbind(this)
    }

    override fun changeView(beacon: BeaconReceived) {
        val navDirections: NavDirections =
            BeaconListDirections.actionBeaconToBeaconDescription(
                beacon
            )
        Navigation.findNavController(requireView()).navigate(navDirections)
    }
}


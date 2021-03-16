package com.mateuscostaluz.presensor.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mateuscostaluz.presensor.R


class MainActivity : AppCompatActivity(R.layout.main_activity) {
    protected val TAG = "MainActivityTeste"

    private val PERMISSION_REQUEST_FINE_LOCATION = 1
    private val PERMISSION_REQUEST_BACKGROUND_LOCATION = 2
    private val PERMISSION_REQUEST_BLUETOOTH = 3

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSION_REQUEST_BLUETOOTH -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Bluetooth permission granted")
                    Toast.makeText(this, "Bluetooth permission granted",Toast.LENGTH_LONG).show()
                } else {
                    Log.d(TAG, "Bluetooth permission not granted")
                    Toast.makeText(this, "Bluetooth not permission granted",Toast.LENGTH_LONG).show()
                }
            }
            PERMISSION_REQUEST_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Access fine location permission granted")
                    Toast.makeText(this, "Access fine location not permission granted",Toast.LENGTH_LONG).show()
                } else {
                    Log.d(TAG, "Access fine location permission not granted")
                    Toast.makeText(this, "Access fine location not permission granted",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
package com.mateuscostaluz.presensor.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.mateuscostaluz.presensor.global.Global
import com.google.android.material.textfield.TextInputLayout
import com.mateuscostaluz.presensor.R
import com.mateuscostaluz.presensor.models.User
import com.mateuscostaluz.presensor.services.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class Login: Fragment(R.layout.login) {

    protected val TAG = "LOGIN"
    private val PERMISSION_REQUEST_FINE_LOCATION = 1

    private val retrofitService by inject<RetrofitService>()
    private val global by inject<Global>()
    private lateinit var confirmButton: Button
    private lateinit var registerButton: Button
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionAccessFineLocation()
        initVariables(view)
        isLogged()
        setListener(view)
    }

    private fun isLogged() {
        var jwtValue = (global.jwt as MutableLiveData<String>).value
        jwtValue?.let {
            Navigation.findNavController(requireView()).navigate(R.id.beacon)
        }
    }

    private fun setListener(view: View) {
        registerButton.setOnClickListener {
            val navDirections: NavDirections =
                LoginDirections.actionLoginToSignUp()
            Navigation.findNavController(requireView()).navigate(navDirections)
        }

        confirmButton.setOnClickListener {
            var user = User(email.editText?.text.toString(), password.editText?.text.toString())
            retrofitService.login(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({


                    if(it.status != 200){
                        Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG).show()
                    }else{
                        Log.i("Received : ", it.toString())
                        Log.i("Received : ", it.data)
                        //                    (global.user as MutableLiveData<UserReceived>).value = it
                        (global.jwt as MutableLiveData<String>).value = it.data
                        val navDirections: NavDirections =
                            LoginDirections.actionLoginToBeacon()
                        Navigation.findNavController(requireView()).navigate(navDirections)
                    }

                }, {
                    Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG).show()
                    Log.e("Error aqui 2", it.toString())
                })
        }
    }

    private fun initVariables(view: View) {
        confirmButton = view.findViewById<Button>(R.id.login_confirm)
        registerButton = view.findViewById<Button>(R.id.login_signup)
        email = view.findViewById<TextInputLayout>(R.id.login_email)
        password = view.findViewById<TextInputLayout>(R.id.login_password)
    }

    private fun permissionAccessFineLocation() {
        val permissionCheck = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Log.i(TAG, "Permission needed")
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_FINE_LOCATION
                )
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_FINE_LOCATION
                )
            }
        } else {
            Toast.makeText(requireContext(), "Permission (already) Granted!", Toast.LENGTH_LONG).show()
        }
    }
}
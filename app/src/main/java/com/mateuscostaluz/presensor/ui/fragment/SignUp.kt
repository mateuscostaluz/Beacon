package com.mateuscostaluz.presensorn.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import com.mateuscostaluz.presensor.R
import com.mateuscostaluz.presensor.models.UserReceived
import com.mateuscostaluz.presensor.services.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class SignUp : Fragment(R.layout.register) {

    private val retrofitService by inject<RetrofitService>()
    private lateinit var confirmButton: Button
    private lateinit var email: TextInputLayout
    private lateinit var name: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var ra: TextInputLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables(view)
        setListener(view)
    }

    private fun initVariables(view: View) {
        confirmButton = view.findViewById<Button>(R.id.signup_confirm)
        email = view.findViewById<TextInputLayout>(R.id.signup_email)
        name = view.findViewById<TextInputLayout>(R.id.signup_name)
        password = view.findViewById<TextInputLayout>(R.id.signup_password)
        ra = view.findViewById<TextInputLayout>(R.id.signup_ra)
    }

    private fun setListener(view: View) {
        confirmButton.setOnClickListener {
            Log.i("Exist", retrofitService.toString())
            var user = UserReceived(
                ra.editText?.text.toString(),
                email.editText?.text.toString(),
                password.editText?.text.toString(),
                name.editText?.text.toString()
            )
            retrofitService.register(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    if(it.status != 200){
                        Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG).show()
                        Log.e("Error", it.toString())
                    }else {
                        Log.i("Found", it.toString())
                        Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG)
                            .show()
                        Navigation.findNavController(requireView()).popBackStack()
                    }
                }, {
                    Toast.makeText(view.context, it.message.toString(), Toast.LENGTH_LONG).show()
                    Log.e("Error", it.toString())
                })
        }
    }
}
package com.mateuscostaluz.presensor.services.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class DialogCreation() {

    fun build(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Aviso!")
        builder.setMessage("Ativar o bluetooth antes de iniciar a busca!")
            .setPositiveButton("OK"
            ) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
        // Create the AlertDialog object and return it
        builder.show()
    }
}
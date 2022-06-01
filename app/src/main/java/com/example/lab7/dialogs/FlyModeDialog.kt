package com.example.lab7.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class FlyModeDialog (private val message: String): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Airplane mode status")
            .setMessage(message)
            .create()
    }
}
package com.example.lab7

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class FlyModeReceiver(private val callback: (Boolean) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isAirplaneModeOn = getAirplaneMode(context)
                    if (isAirplaneModeOn) {
                        sendNotification(context, "Airplane mode is enabled!")
                        callback(true)
                    } else {
                        sendNotification(context, "Airplane mode is disabled!")
                        callback(false)
                    }
                }
            }
        }
    }

    private fun getAirplaneMode(context: Context?) : Boolean {
        return Settings.Global.getInt(context?.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private fun sendNotification(context: Context, message: String) {
        val builder = NotificationCompat.Builder(context, "MESSAGES")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Message!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(context).apply {
            this.notify(0,  builder.build())
        }
    }
}
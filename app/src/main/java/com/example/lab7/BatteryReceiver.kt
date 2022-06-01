package com.example.lab7

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class BatteryReceiver(private val callback: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let{
            intent?.let{
                if(intent?.action == Intent.ACTION_BATTERY_LOW) {
                    sendNotification(context, "Battery is low!")
                    callback()
                }
            }
        }
    }

    private fun sendNotification(context: Context, message: String) {
        val builder = NotificationCompat.Builder(context, "MESSAGES")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Message!")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(context).apply {
            this.notify(0, builder.build())
        }
    }
}

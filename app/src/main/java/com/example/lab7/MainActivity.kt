package com.example.lab7

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab7.databinding.ActivityMainBinding
import com.example.lab7.dialogs.BatteryDialog
import com.example.lab7.dialogs.CameraDialog
import com.example.lab7.dialogs.FlyModeDialog


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val batteryReceiver: BroadcastReceiver = BatteryReceiver {
            val dialog = BatteryDialog()
            dialog.show(supportFragmentManager, "dlg")
    }

    private val cameraReceiver: BroadcastReceiver = CameraReceiver{
        val dialog = CameraDialog()
        dialog.show(supportFragmentManager, "dlg")
    }

    private val flyModeReceiver: BroadcastReceiver = FlyModeReceiver{
        if(it){
            val dialog = FlyModeDialog("Airplane mode is enabled!")
            dialog.show(supportFragmentManager, "dlg")
        } else {
            val dialog = FlyModeDialog("Airplane mode is disabled!")
            dialog.show(supportFragmentManager, "dlg")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_LOW)
        registerReceiver(batteryReceiver, batteryFilter)

        val cameraFilter = IntentFilter(Intent.ACTION_CAMERA_BUTTON)
        registerReceiver(cameraReceiver, cameraFilter)

        val flyModeFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(flyModeReceiver, flyModeFilter)

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryReceiver)
        unregisterReceiver(cameraReceiver)
        unregisterReceiver(flyModeReceiver)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "MESSAGES",
                "Messages",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Incoming messages from broadcast receivers"

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
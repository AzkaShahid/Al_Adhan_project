package com.app.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            // Network connection is lost
            Toast.makeText(context, "Network connection lost", Toast.LENGTH_SHORT).show()
        } else {
            // Network connection is regained
            Toast.makeText(context, "Network connection regained", Toast.LENGTH_SHORT).show()
        }
    }
}
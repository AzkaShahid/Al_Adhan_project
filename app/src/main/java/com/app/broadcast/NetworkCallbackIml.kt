package com.app.broadcast

import android.net.ConnectivityManager
import android.net.Network


class NetworkCallbackImpl(private val onNetworkStateChanged: (isConnected: Boolean) -> Unit) :
    ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        onNetworkStateChanged(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        onNetworkStateChanged(false)
    }
}

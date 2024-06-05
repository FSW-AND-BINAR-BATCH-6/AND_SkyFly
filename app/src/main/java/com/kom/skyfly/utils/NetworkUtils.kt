package com.kom.skyfly.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/

fun checkInternetConnection(context: Context) {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork?.isConnected != true) {
        throw NoInternetException()
    }
}

suspend fun performNetworkOperation(
    context: Context,
    operation: suspend () -> Unit,
) {
    try {
        checkInternetConnection(context)
        operation()
    } catch (e: NoInternetException) {
        // Handle NoInternetException
        // ex: show a message to the user
    } catch (e: Exception) {
        // Handle other exceptions
    }
}

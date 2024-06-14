package com.kom.skyfly.utils

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/

// fun checkInternetConnection(context: Context) {
//    val connectivityManager =
//        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
//    Log.d(
//        "NetworkCheck",
//        "Active Network: $activeNetwork, Is Connected: ${activeNetwork?.isConnected}",
//    )
//    if (activeNetwork?.isConnected != true) {
//        throw NoInternetException()
//    }
// }
//
// suspend fun performNetworkOperation(
//    context: Context,
//    operation: suspend () -> Unit,
// ) {
//    try {
//        checkInternetConnection(context)
//        operation()
//    } catch (e: NoInternetException) {
//        delay(1000)
//        Toasty.error(context, "No internet connection", Toasty.LENGTH_SHORT).show()
//    } catch (e: Exception) {
//        Log.e("NetworkError", "An error occurred: ${e.message}")
//    }
// }

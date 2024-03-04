package com.app.application

import android.content.Context
import com.app.utils.AppExecutor
import com.app.bases.BaseApplication


/**
 * @author Muzzamil Saleem
 */
class ApplicationClass : BaseApplication() {


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        AppExecutor.executorService!!.submit {
//            if (BuildConfig.DEBUG) {
//                StrictMode.setThreadPolicy(
//                    StrictMode.ThreadPolicy.Builder()
//                        .detectDiskReads()
//                        .detectDiskWrites()
//                        .detectNetwork() // or .detectAll() for all detectable problems
//                        .penaltyLog()
//                        .build()
//                )
//                StrictMode.setVmPolicy(
//                    StrictMode.VmPolicy.Builder()
//                        .detectLeakedSqlLiteObjects()
//                        .penaltyLog()
//                        .penaltyDeath()
//                        .build()
//                )
//
//            }
        }


    }

    companion object {
        fun getInstance(): Context {
            return appContext
        }

        lateinit var appContext: Context
    }


}
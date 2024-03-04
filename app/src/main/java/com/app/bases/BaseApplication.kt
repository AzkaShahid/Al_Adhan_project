package com.app.bases

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Muzzamil Saleem
 */
@HiltAndroidApp
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}

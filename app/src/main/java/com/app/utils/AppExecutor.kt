package com.app.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
/**
 * @author Muzzamil Saleem
 */
object AppExecutor {
    private var sInstance: ExecutorService? = null
    val executorService: ExecutorService?
        get() {
            if (sInstance == null) {
                sInstance = Executors.newFixedThreadPool(3)
            }
            return sInstance
        }
}
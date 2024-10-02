package com.app.preferences

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * @author Muzzamil Saleem
 */
object PreferenceUtility {
    /*
     * Function for getting a value from shared preferences
     */
    fun getPreference(
        baseActivity: Context,
        prefName: String?,
        key: String?
    ): String? {
        val usePref = baseActivity.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        return usePref.getString(key, "")
    }

    /*
     * Function for setting values for shared preferences
     */
    fun setPreference(
        base: Context,
        prefName: String?,
        key: String?,
        value: String?
    ) {
        val userPref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        val editor = userPref.edit()
        editor.putString(key, value)
        editor.apply()
    }
    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun setBoolPreference(
        base: Context,
        prefName: String?,
        key: String?,
        value: Boolean
    ) {
        val userPref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        val editor = userPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolPreference(
        base: Context,
        prefName: String?,
        key: String?
    ): Boolean {
        val usePref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        return usePref.getBoolean(key, false)
    }

    fun getBoolPreference(
        base: Context,
        prefName: String?,
        key: String?, boolean: Boolean
    ): Boolean {
        val usePref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        return usePref.getBoolean(key, boolean)
    }

    fun getBoolPrefsWithDefault(
        base: Context,
        prefName: String?,
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        val usePref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        return usePref.getBoolean(key, defaultValue)
    }

    fun getBoolPreferenceSettings(
        base: Context,
        prefName: String?,
        key: String?
    ): Boolean {
        val usePref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        return usePref.getBoolean(key, true)
    }

    fun setIntPreference(
        base: Context,
        prefName: String?,
        key: String?,
        value: Int
    ) {
        val userPref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        val editor = userPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getIntPreference(
        base: Context,
        prefName: String?,
        key: String?
    ): Int {
        val usePref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        return usePref.getInt(key, 0)
    }

    fun removeAllPreferences(base: Context, prefName: String?) {
        val userPref = base.getSharedPreferences(
            prefName,
            Context.MODE_PRIVATE
        )
        val editor = userPref.edit()
        editor.clear()
        editor.apply()
    }
}
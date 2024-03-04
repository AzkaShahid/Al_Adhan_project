package com.app.preferences

import android.content.Context
import com.app.application.ApplicationClass
import com.app.models.login.LoggedInUserData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Muzzamil Saleem
 */
object PreferencesHelper {

    private const val APP_PREFS = "APP_PREFS"

    fun setPrefs(key: String?, value: String?) {
        PreferenceUtility.setPreference(
            ApplicationClass.appContext,
            APP_PREFS,
            key,
            value
        )
    }

    fun setPrefs(key: String?, value: Int) {
        PreferenceUtility.setIntPreference(
            ApplicationClass.appContext,
            APP_PREFS,
            key,
            value
        )
    }

    fun setPrefs(key: String?, value: Boolean) {
        PreferenceUtility.setBoolPreference(
            ApplicationClass.appContext,
            APP_PREFS,
            key,
            value
        )
    }

    fun getStringPrefs(key: String, context: Context? = ApplicationClass.appContext): String? {
        var context_ = context
        if (context_ == null) {
            context_ = ApplicationClass.appContext
        }
        return PreferenceUtility.getPreference(
            context_,
            APP_PREFS,
            key
        )
    }

    fun getIntPrefs(key: String?): Int {
        return PreferenceUtility.getIntPreference(
            ApplicationClass.appContext,
            APP_PREFS,
            key
        )
    }


    fun saveLoginCredentials(login: String, password: String, isChecked: Boolean) {
        setPrefs("USER_EMAIL", login)
        setPrefs("USER_PASSWORD", password)
        setPrefs("IS_CHECKED", isChecked)
    }

    fun saveUserDataList(userDataList: ArrayList<LoggedInUserData>) {
        val existingList = getUserDataList()

        for (userData in userDataList) {
            val existingUserData = existingList.find { it.email == userData.email }
            if (existingUserData == null) {
                existingList.add(userData)
            } else {
                if (existingUserData != userData) {
                    existingUserData.isRemember = userData.isRemember
                }
            }
        }
        val gson = Gson()
        val updatedListJson = gson.toJson(existingList)
        setPrefs("USER_DATA_LIST", updatedListJson)
    }


    private fun getUserDataList(): ArrayList<LoggedInUserData> {
        val userDataListJson = getStringPrefs("USER_DATA_LIST")

        if (userDataListJson != "") {
            val gson = Gson()
            return gson.fromJson(
                userDataListJson,
                object : TypeToken<ArrayList<LoggedInUserData>>() {}.type
            )
        }

        return ArrayList()
    }


}
package com.app.constants

import android.graphics.Color
import com.app.models.MenuItemModel


const val DATABASE_NAME = "app-database"


object AppConstants {

    const val IMAGE_DIRECTORY_NAME = "AppDirectory"
    var SELECTED_MENU_ID: MenuItemModel.MenuID = MenuItemModel.MenuID.HOME


    const val DISPLAY_DATE = "EEE, dd MMM yyyy"
    const val DISPLAY_GET = "dd/MM/yyyy"
    const val DATE_FORMAT_ = "yyyy-MM-dd"
    const val TIME_FORMAT_ = "HH:mm:ss"
    const val TIME_FORMAT_SEND = "HH:mm"
    const val TIME_DISPLAY_ = "hh:mm aa"


    object PrefConstants {
        const val APP_PREFS = "APP_PREFS"
        const val IS_LOGIN = "IS_LOGIN"
        const val USER_DATA = "USER_DATA"
        const val USER_ID = "USER_ID"
        const val USER_INFO = "USER_INFO"
        const val MEETING_DETAILS = "MEETING_DETAILS"
        const val MEETING_NAME = "MEETING_NAME"
        const val CONTACTS_LIST = "CONTACTS_LIST"
        const val CONTACT_DETAILS = "CONTACT_DETAILS"
        const val ROLE_LIST = "ROLE_LIST"
        const val APP_TOKEN = "APP_TOKEN"
        const val FIREBASE_TOKEN = "FIREBASE_TOKEN"


    }


    object WebURL {
        const val USER_AGENT = "V-Join-Life-App"
        const val BASE_URL = "https://www.wejoinlife.com/vconnect/users/app/"
        const val LOGIN = "validateuser.jsp"

    }

    object NestedProgress {
        val COLOR_INNER = Color.parseColor("#8B0013")
        val COLOR_OUTER = Color.parseColor("#338B0013")

        const val CIRCLE_RADIUS = 360
        const val ANIM_DURATION = 1000
        const val INNER_ANIM_INTERPOLATOR = 6
        const val OUTER_ANIM_INTERPOLATOR = 5
        const val INNER_LOADER_LENGTH = 260F
        const val OUTER_LOADER_LENGTH = 320F
        const val INNER_STROKE_WIDTH = 4F
        const val OUTER_STROKE_WIDTH = 4.5F
        const val MID_POINT = 2F
        const val START_POINT = 0F
        const val DESIRED_WH = 70F
        const val MAX_TOTAL_STROKE = 21F
        const val MIN_STOKE = 0F
        const val MAX_STROKE = 10F
        const val MIN_B_CIRCLES = 0F
        const val MAX_B_CIRCLES = 10F
        const val SPACE_BETWEEN_CIRCLES = 3F

    }


}
package com.app.models

class MenuItemModel(var menuID: MenuID, var iconID: Int, var menuText: String) {
    enum class MenuID {
        HOME,
        SETTINGS,
        USER_PROFILE,

    }
}
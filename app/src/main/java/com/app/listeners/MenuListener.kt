package com.app.listeners

import com.app.models.MenuItemModel

interface MenuListener {

    fun onMenuItemClicked(menuItem: MenuItemModel)
    fun getMenuItems(): ArrayList<MenuItemModel>

}
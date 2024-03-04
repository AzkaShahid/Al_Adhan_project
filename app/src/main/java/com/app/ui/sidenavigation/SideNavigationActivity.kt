package com.app.ui.sidenavigation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.models.MenuItemModel
import com.app.listeners.MenuListener
import com.app.ui.adapters.MenuItemAdapter
import com.app.ui.main.MainViewModel
import com.app.databinding.ActivitySideNavigationBinding
import com.app.bases.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SideNavigationActivity : BaseActivity<ActivitySideNavigationBinding, MainViewModel>(),
    MenuListener,
    NavigationBarView.OnItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: MenuItemAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override val mViewModel: MainViewModel by viewModels()

    override fun initBinding() = ActivitySideNavigationBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {
        navController = findNavController(R.id.nav_host_fragment_content_side_navigation)
        bottomNavigationView = mViewBinding.appBarSideNavigation.bottomNav
        drawerLayout = mViewBinding.drawerLayout
        adapter = MenuItemAdapter(this, this)
        recycler = mViewBinding.drawerLayout.findViewById(R.id.recyclerView)
        recycler.adapter = adapter
    }

    override fun addViewModelObservers() {

    }

    override fun attachListens() {

        bottomNavigationView.setOnItemSelectedListener(this)

    }


    override fun onMenuItemClicked(menuItem: MenuItemModel) {
        openCloseDrawer()
        drawerLayout.close()
        updateNavigation(menuItem.menuID)
    }


    override fun getMenuItems(): ArrayList<MenuItemModel> {
        val list = ArrayList<MenuItemModel>()
        list.add(MenuItemModel(MenuItemModel.MenuID.HOME, R.drawable.ic_home_black_24dp, getString(R.string.menu_home)))
        list.add(MenuItemModel(MenuItemModel.MenuID.SETTINGS, R.drawable.ic_home_black_24dp, getString(R.string.menu_settings)))
        list.add(MenuItemModel(MenuItemModel.MenuID.USER_PROFILE, R.drawable.ic_home_black_24dp, getString(R.string.menu_user_profile)))

        return list
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                updateNavigation(MenuItemModel.MenuID.HOME)
            }
            R.id.navigation_settings -> {
                updateNavigation(MenuItemModel.MenuID.SETTINGS)
            }
            else -> {
                updateNavigation(MenuItemModel.MenuID.USER_PROFILE)
            }
        }
        return true
    }


    private fun updateNavigation(menuID: MenuItemModel.MenuID, stopBackStack: Boolean = false) {
        if (stopBackStack) navController.popBackStack()
        when (menuID) {
            MenuItemModel.MenuID.HOME -> {
                navController.navigate(R.id.navigation_home)
            }
            MenuItemModel.MenuID.SETTINGS -> {
                navController.navigate(R.id.navigation_settings)
            }
            else -> {
                navController.navigate(R.id.navigation_profile)
            }
        }
    }

    open fun openCloseDrawer() {
        if (drawerLayout.isOpen) {
            drawerLayout.close()
        } else {
            drawerLayout.open()
        }
    }

}
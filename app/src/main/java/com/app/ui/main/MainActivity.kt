package com.app.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.app.ui.sidenavigation.SideNavigationActivity
import com.app.databinding.ActivityMainBinding
import com.app.bases.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val mViewModel: MainViewModel by viewModels()

    override fun initBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {
        startActivity(Intent(this, SideNavigationActivity::class.java))
    }

    override fun addViewModelObservers() {
    }

    override fun attachListens() {
    }


}
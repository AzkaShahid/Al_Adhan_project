package com.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.app.ui.main.MainViewModel
import com.app.ui.sidenavigation.SideNavigationActivity
import com.app.databinding.ActivitySplashBinding
import com.app.bases.BaseActivity
import com.app.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, MainViewModel>() {

    private val delay: Long = 2000
    override val mViewModel: MainViewModel by viewModels()


    override fun initBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {

        Handler(Looper.getMainLooper()).postDelayed({
            startNextActivity()
        }, delay)
    }


    override fun addViewModelObservers() {
    }

    override fun attachListens() {
    }

    private fun startNextActivity() {
        startActivity(Intent(this, SideNavigationActivity::class.java))
        this.finish()
    }


}
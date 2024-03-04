package com.app.ui.sidenavigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.R
import com.app.databinding.FragmentHomeBinding
import com.app.bases.BaseFragment
import com.app.databinding.FragmentGalleryBinding

class ProfileFragment : BaseFragment<FragmentGalleryBinding, ProfileViewModel>() {

    override val mViewModel: ProfileViewModel by viewModels()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGalleryBinding {
        return FragmentGalleryBinding.inflate(inflater, container, false)
    }

    override fun getToolbarBinding() = mViewBinding.toolbar

    override fun getToolbarTitle() = R.string.menu_user_profile

    override fun isMenuButton() = true


    override fun setupUI(savedInstanceState: Bundle?) {
    }

    override fun attachListener() {
    }

    override fun observeViewModel() {
    }
}
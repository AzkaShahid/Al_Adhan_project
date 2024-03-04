package com.app.ui.sidenavigation.profile


import com.app.bases.BaseViewModel
import com.app.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val repository: MainRepository
) : BaseViewModel() {


}

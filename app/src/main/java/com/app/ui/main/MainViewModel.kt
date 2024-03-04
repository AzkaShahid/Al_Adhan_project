package com.app.ui.main

import com.app.bases.BaseViewModel
import com.app.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: MainRepository
) : BaseViewModel() {

}
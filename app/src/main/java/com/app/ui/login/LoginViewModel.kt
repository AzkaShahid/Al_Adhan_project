package com.app.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.bases.BaseViewModel
import com.app.models.login.LoginUserResponse
import com.app.network.Resource
import com.app.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    val loginResponse: MutableLiveData<Resource<LoginUserResponse>> = MutableLiveData()

    fun callLogin(
        userName: String,
        password: String
    ) = viewModelScope.launch {
        loginResponse.value = Resource.Loading
        loginResponse.value = repository.callLogin(userName, password)
    }

}
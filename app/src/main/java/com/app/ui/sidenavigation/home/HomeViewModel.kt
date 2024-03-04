package com.app.ui.sidenavigation.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.bases.BaseViewModel
import com.app.models.prayer.PrayerResponse
import com.app.network.Resource
import com.app.respository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    val prayerResponse: MutableLiveData<Resource<PrayerResponse>> = MutableLiveData()

    fun callGetPrayerData(apiUrl : String) = viewModelScope.launch {
        prayerResponse.value = Resource.Loading
        prayerResponse.value = repository.callPrayerData(apiUrl)
    }
}

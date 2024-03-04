package com.app.extention

import android.app.Activity
import com.app.R
import com.app.network.Resource


fun Activity.handleApiErrorWithToast(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> {
//            showToast(getString(R.string.please_check_your_internet_connection))
        }

        else -> {
//            AppToast.showErrorToast(this, failure.errorString)
        }
    }
}



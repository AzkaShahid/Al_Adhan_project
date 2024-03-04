package com.app.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.app.R

class TransparentProgressDialog(context: Context) :
    Dialog(context, R.style.TransparentProgressDialog) {

    companion object {
        var dialog: TransparentProgressDialog? = null
        fun showProgressDialog(activity: Activity) {
            if (dialog == null) {
                dialog = TransparentProgressDialog(activity)
                dialog!!.setCancelable(false)
                dialog!!.show()
            } else {
                try {
                    if (!dialog!!.isShowing) dialog!!.show()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }

        fun hideProgress() {
            if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
            dialog = null
        }
    }

    init {
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        setContentView(R.layout.loading_dialog)

    }
}
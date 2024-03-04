package com.app.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.app.R
import com.app.bases.BaseActivity
import com.app.databinding.ActivityLoginBinding
import com.app.models.login.LoggedInUserData
import com.app.models.login.LoginUserResponse
import com.app.network.Resource
import com.app.preferences.PreferencesHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val mViewModel: LoginViewModel by viewModels()

    override fun initBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initViews(savedInstanceState: Bundle?) {
        mViewBinding.etUserName.setText("b00002@yopmail.com")
        mViewBinding.etPassword.setText("a123123B")
    }

    override fun addViewModelObservers() {
        mViewModel.loginResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    hideProgressDialog()
                    try {
                        val createUserResponse = it.value
                        if (createUserResponse.statusCode == 0) {
                            onLoginSuccess(createUserResponse)

                        } else if (createUserResponse.statusCode == 300) {
                            MaterialAlertDialogBuilder(this)
                                .setTitle(R.string.dialog_title_error)
                                .setMessage(createUserResponse.message)
                                .setPositiveButton(R.string.ok, null)
                                .show()
                        } else if (createUserResponse.statusCode == 1) {
                            MaterialAlertDialogBuilder(this)
                                .setTitle(R.string.dialog_title_error)
                                .setMessage(R.string.no_account)
                                .setPositiveButton(R.string.ok, null)
                                .show()

                        } else if (createUserResponse.statusCode == 200 && createUserResponse.message.equals(
                                "Account ID or password is incorrect"
                            )
                        ) {
                            MaterialAlertDialogBuilder(this)
                                .setTitle(R.string.dialog_title_error)
                                .setMessage(R.string.no_account)
                                .setPositiveButton(R.string.ok, null)
                                .show()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                is Resource.Failure -> {
                    hideProgressDialog()
                }

                Resource.Loading -> {
                    showProgressDialog()
                }
            }
        }
    }


    override fun attachListens() {
        mViewBinding.btnLogin.setOnClickListener {
            callLogin()

        }


    }

    private fun callLogin() {
        with(mViewBinding) {
            if (etUserName.text.isNullOrEmpty()) {
                etUserName.error = "Please enter username or password"
            } else if (etPassword.text.isNullOrEmpty()) {
                etPassword.error = "Please enter password"
            } else {
                val email = etUserName.text.toString()
                val password = etPassword.text.toString()
                mViewModel.callLogin(email, password)
            }
        }
    }

    private fun onLoginSuccess(createUserResponse: LoginUserResponse) {

        with(mViewBinding) {
            val email = etUserName.text.toString()
            val password = etPassword.text.toString()
            val userDataList = ArrayList<LoggedInUserData>()
            userDataList.add(LoggedInUserData(email, password, cbRememberMe.isChecked))

            PreferencesHelper.saveUserDataList(userDataList)
            PreferencesHelper.saveLoginCredentials(
                email,
                password,
                cbRememberMe.isChecked
            )

        }


    }


}
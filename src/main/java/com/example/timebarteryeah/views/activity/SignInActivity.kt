package com.example.timebarteryeah.views.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.SignInContract
import com.example.timebarteryeah.models.constant.Constant
import com.example.timebarteryeah.presenters.SignInPresenter
import com.example.timebarteryeah.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_in_sign.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import javax.inject.Inject

class SignInActivity : BaseActivity(), SignInContract.View {

    @Inject lateinit var presenter: SignInPresenter

    override fun getContentView(): Int = R.layout.activity_in_sign

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val prefs = getSharedPreferences(Constant.Tag.REMEMBER_ME, Context.MODE_PRIVATE)
        if(!prefs.getBoolean(Constant.Tag.REMEMBER_ME, true)) {
            presenter.setRememberMe()
        }

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            presenter.setSignIn(
                    this@SignInActivity,
                    email,
                    password
            )
        }

        tvToSignUp.setOnClickListener {
            startActivity(intentFor<SignUpActivity>()
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        cbRememberMe.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                prefs.edit()
                    .putBoolean(Constant.Tag.REMEMBER_ME, false)
                    .apply()
            } else {
                prefs.edit()
                    .clear()
                    .apply()
            }
        }

    }

    override fun getProgressBar(): View = rlProgressBar

    override fun onSuccessSignIn() {
        toast(getString(R.string.text_message_sign_in_success))
        toMainActivity()
    }

    override fun onRememberMe() {
        toMainActivity()
    }

    private fun toMainActivity() {
        startActivity(intentFor<MainActivity>()
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

}
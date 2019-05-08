package com.example.timebarteryeah.views.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.SignUpContract
import com.example.timebarteryeah.presenters.SignUpPresenter
import com.example.timebarteryeah.views.base.BaseActivity
import kotlinx.android.synthetic.main.activity_up_sign.*
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class SignUpActivity : BaseActivity(), SignUpContract.View {

    @Inject lateinit var presenter: SignUpPresenter

    override fun getContentView(): Int = R.layout.activity_up_sign

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        btnSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            presenter.setSignUp(this@SignUpActivity, name, email, password)
        }

        tvToSignIn.setOnClickListener { toSignInActivity() }

    }

    override fun getProgressBar(): View = rlProgressBar

    override fun onSuccessSignUp() {
        AlertDialog.Builder(this@SignUpActivity)
            .setTitle(getString(R.string.text_title_alert_sign_up_success))
            .setMessage(getString(R.string.text_message_alert_sign_up_success))
            .setPositiveButton(getString(R.string.text_btn_alert_confirm)) { _, _ -> toSignInActivity() }
            .setCancelable(false)
            .show()
    }

    private fun toSignInActivity() {
        startActivity(intentFor<SignInActivity>()
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }

}
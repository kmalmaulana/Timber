package com.example.timebarteryeah.contracts

import android.content.Context
import com.example.timebarteryeah.views.base.BaseView

interface SignInContract {

    interface View : BaseView {
        fun onSuccessSignIn()
        fun onRememberMe()
    }

    interface Presenter {
        fun setSignIn(context: Context, email: String?, password: String?)
        fun setRememberMe()
    }

}
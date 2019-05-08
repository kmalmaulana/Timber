package com.example.timebarteryeah.contracts

import android.content.Context
import com.example.timebarteryeah.views.base.BaseView

interface SignUpContract {

    interface View : BaseView {
        fun onSuccessSignUp()
    }

    interface Presenter {
        fun setSignUp(context: Context, name: String?, email: String?, password: String?)
    }

}
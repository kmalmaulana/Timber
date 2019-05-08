package com.example.timebarteryeah.contracts

import android.content.Context
import android.net.Uri
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.views.base.BaseView

interface MyProfileContract {

    interface View : BaseView {
        fun onSuccessProfileView(user: User?)
        fun onSignOut()
    }

    interface Presenter {
        fun getProfileView()
        fun setPhoto(context: Context, uri: Uri?)
        fun doSignOut()
    }

}
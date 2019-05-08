package com.example.timebarteryeah.contracts

import android.content.Context
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.views.base.BaseView

interface AcceptPostContract {

    interface View : BaseView {
        fun onAcceptPosts(postList: List<Post>, userList: List<User>)
    }

    interface Presenter {
        fun getAcceptPosts(context: Context)
    }

}
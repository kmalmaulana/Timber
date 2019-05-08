package com.example.timebarteryeah.contracts

import android.content.Context
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.views.base.BaseView

interface MyPostContract {

    interface View : BaseView {
        fun onMyPosts(postList: List<Post>, userList: List<User>)
    }

    interface Presenter {
        fun getMyPosts(context: Context)
    }

}
package com.example.timebarteryeah.contracts

import android.content.Context
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.views.base.BaseView

interface HomeContract {

    interface View : BaseView {
        fun onTimeBalanceAndSpent(user: User?)
        fun onAllPost(postList: List<Post?>, userList: List<User?>)
    }

    interface Presenter {
        fun getTimeBalanceAndSpent()
        fun getLastPost(context: Context)
    }

}
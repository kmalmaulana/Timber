package com.example.timebarteryeah.contracts

import android.content.Context
import com.example.timebarteryeah.views.base.BaseView

interface DetailPostContract {

    interface View : BaseView {
        fun onToListHelp()
        fun onDonePost()
    }

    interface Presenter {
        fun setToListHelp(context: Context, documentId: String?)
        fun setDonePost(context: Context, documentId: String)
    }

}
package com.example.timebarteryeah.views.base

import android.view.View

open interface BaseView {

    fun getProgressBar(): View
    fun onShowLoading()
    fun onHideLoading()

    fun onMessage(message: String?)
    fun onException(message: String?)

}
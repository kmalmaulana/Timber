package com.example.timebarteryeah.views.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.timebarteryeah.extensions.gone
import com.example.timebarteryeah.extensions.visible
import dagger.android.AndroidInjection
import org.jetbrains.anko.toast

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this@BaseActivity)
        super.onCreate(savedInstanceState)
        setContentView(getContentView())

        onActivityCreated(savedInstanceState)

    }

    abstract fun getContentView(): Int
    abstract fun onActivityCreated(savedInstanceState: Bundle?)

    override fun onShowLoading() {
        getProgressBar().visible()
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onHideLoading() {
        getProgressBar().gone()
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onMessage(message: String?) { toast(message.toString()) }
    override fun onException(message: String?) { toast(message.toString()) }

}
package com.example.timebarteryeah.views.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.timebarteryeah.extensions.gone
import com.example.timebarteryeah.extensions.visible
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.toast

abstract class BaseFragment : Fragment(), BaseView, AnkoLogger {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this@BaseFragment)
        return inflater.inflate(getContentView(), container, false)
    }

    abstract fun getContentView(): Int

    override fun onShowLoading() {
        try {
            getProgressBar().visible()
            activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } catch (e: TypeCastException) {
            error(e.message)
        }
    }

    override fun onHideLoading() {
        try {
            getProgressBar().gone()
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } catch (e: TypeCastException) {
            error(e.message)
        }
    }

    override fun onMessage(message: String?) { activity?.toast(message.toString()) }
    override fun onException(message: String?) { activity?.toast(message.toString()) }

}
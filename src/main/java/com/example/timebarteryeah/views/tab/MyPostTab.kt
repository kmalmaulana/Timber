package com.example.timebarteryeah.views.tab

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.MyPostContract
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.presenters.MyPostPresenter
import com.example.timebarteryeah.views.activity.MainActivity
import com.example.timebarteryeah.views.adapter.PostAdapter
import com.example.timebarteryeah.views.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_help_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

class MyPostTab : BaseFragment(), MyPostContract.View, AnkoLogger {

    private lateinit var ui: View

    @Inject lateinit var presenter: MyPostPresenter

    override fun getContentView(): Int = R.layout.tab_help_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui = view

        presenter.getMyPosts(activity!!)

    }

    override fun getProgressBar(): View {
        val ui = activity as MainActivity
        return ui.rlProgressBar
    }

    override fun onMyPosts(postList: List<Post>, userList: List<User>) {
        try {
            ui.rvListHelp.layoutManager = LinearLayoutManager(activity)
            ui.rvListHelp.adapter = PostAdapter(activity!!, postList, userList)
            (ui.rvListHelp.adapter as PostAdapter).notifyDataSetChanged()
        } catch (e: KotlinNullPointerException) {
            error(e.message)
        }
    }

}
package com.example.timebarteryeah.views.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.HomeContract
import com.example.timebarteryeah.models.Post
import com.example.timebarteryeah.models.User
import com.example.timebarteryeah.presenters.HomePresenter
import com.example.timebarteryeah.views.activity.MainActivity
import com.example.timebarteryeah.views.adapter.PostAdapter
import com.example.timebarteryeah.views.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeContract.View, AnkoLogger {

    private lateinit var ui: View

    @Inject lateinit var presenter: HomePresenter

    override fun getContentView(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui = view

        presenter.getTimeBalanceAndSpent()
        presenter.getLastPost(activity!!)

    }

    override fun getProgressBar(): View {
        val ui = activity as MainActivity
        return ui.rlProgressBar
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeBalanceAndSpent(user: User?) {
        ui.tvTimeBalance.text = "${user?.timeBalance}H"
        ui.tvTimeSpent.text = "${user?.timeSpent}H"
    }

    override fun onAllPost(postList: List<Post?>, userList: List<User?>) {
        try {
            ui.rvHome.layoutManager = LinearLayoutManager(activity)
            ui.rvHome.adapter = PostAdapter(activity!!, postList, userList)
            (ui.rvHome.adapter as PostAdapter).notifyDataSetChanged()
        } catch (e: KotlinNullPointerException) {
            error(e.message)
        }
    }

}
package com.example.timebarteryeah.views.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.timebarteryeah.R
import com.example.timebarteryeah.contracts.LeaderBoardContract
import com.example.timebarteryeah.models.LeaderBoard
import com.example.timebarteryeah.presenters.LeaderBoardPresenter
import com.example.timebarteryeah.views.activity.MainActivity
import com.example.timebarteryeah.views.adapter.LeaderBoardAdapter
import com.example.timebarteryeah.views.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_board_leader.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

class LeaderBoardFragment : BaseFragment(), LeaderBoardContract.View, AnkoLogger {

    private lateinit var ui: View

    @Inject lateinit var presenter: LeaderBoardPresenter

    override fun getContentView(): Int = R.layout.fragment_board_leader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui = view

        presenter.getLeaderBoard()
    }

    override fun getProgressBar(): View {
        val ui = activity as MainActivity
        return ui.rlProgressBar
    }

    override fun onLeaderBoard(leaderBoardList: List<LeaderBoard>) {
        try {
            ui.rvLeaderBoard.layoutManager = LinearLayoutManager(activity)
            ui.rvLeaderBoard.adapter = LeaderBoardAdapter(activity!!, leaderBoardList)
            (ui.rvLeaderBoard.adapter as LeaderBoardAdapter).notifyDataSetChanged()
        } catch (e: KotlinNullPointerException) {
            error(e.message)
        }
    }

}
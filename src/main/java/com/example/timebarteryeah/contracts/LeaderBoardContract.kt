package com.example.timebarteryeah.contracts

import com.example.timebarteryeah.models.LeaderBoard
import com.example.timebarteryeah.views.base.BaseView

interface LeaderBoardContract {

    interface View : BaseView {
        fun onLeaderBoard(leaderBoardList: List<LeaderBoard>)
    }

    interface Presenter {
        fun getLeaderBoard()
    }

}
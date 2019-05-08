package com.example.timebarteryeah.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.timebarteryeah.R
import com.example.timebarteryeah.models.LeaderBoard
import kotlinx.android.synthetic.main.adapter_board_leader.view.*

class LeaderBoardAdapter(private val context: Context,
                         private val leaderBoardList: List<LeaderBoard>)
    : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_board_leader, parent, false))

    override fun getItemCount(): Int = leaderBoardList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindLeaderBoard(leaderBoardList[position])
    }

    inner class ViewHolder(private val ui: View) : RecyclerView.ViewHolder(ui) {

        @SuppressLint("SetTextI18n")
        fun bindLeaderBoard(leaderBoard: LeaderBoard) {

            ui.tvRank.text = leaderBoard.rank.toString()
            ui.tvName.text = leaderBoard.name
            ui.tvPoint.text = leaderBoard.point.toString()

        }

    }

}
package com.example.timebarteryeah.presenters

import com.example.timebarteryeah.contracts.LeaderBoardContract
import com.example.timebarteryeah.models.LeaderBoard
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error
import org.jetbrains.anko.onComplete
import javax.inject.Inject

class LeaderBoardPresenter @Inject constructor(private val view: LeaderBoardContract.View,
                                               private val firestore: FirebaseFirestore)
    : LeaderBoardContract.Presenter, AnkoLogger {

    private val leaderBoardList = mutableListOf<LeaderBoard>()

    override fun getLeaderBoard() {
        try {

            doAsync {
                onComplete {

                    view.onShowLoading()
                    firestore.collection("users").orderBy("point", Query.Direction.DESCENDING).limit(20)
                            .addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->

                                if(e != null)
                                    return@EventListener

                                snapshot?.documentChanges?.forEach {
                                    val name = it.document.getString("name")
                                    val point = it.document.getLong("point")

                                    leaderBoardList.add(LeaderBoard(it.newIndex + 1, name!!, point))
                                    view.onLeaderBoard(leaderBoardList)
                                    view.onHideLoading()
                                }

                            })

                }
            }

        } catch (e: KotlinNullPointerException) {
            error(e.message)
        }

    }

}
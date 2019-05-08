package com.example.timebarteryeah.views.fragment.module

import com.example.timebarteryeah.contracts.LeaderBoardContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.LeaderBoardPresenter
import com.example.timebarteryeah.views.fragment.LeaderBoardFragment
import dagger.Binds
import dagger.Module

@Module
abstract class LeaderBoardModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(leaderBoardFragment: LeaderBoardFragment): LeaderBoardContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(leaderBoardPresenter: LeaderBoardPresenter): LeaderBoardContract.Presenter

}
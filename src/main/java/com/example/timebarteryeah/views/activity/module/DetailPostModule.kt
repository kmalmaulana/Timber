package com.example.timebarteryeah.views.activity.module

import com.example.timebarteryeah.contracts.DetailPostContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.DetailPostPresenter
import com.example.timebarteryeah.views.activity.DetailPostActivity
import dagger.Binds
import dagger.Module

@Module
abstract class DetailPostModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(detailPostActivity: DetailPostActivity): DetailPostContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(detailPostPresenter: DetailPostPresenter): DetailPostContract.Presenter

}
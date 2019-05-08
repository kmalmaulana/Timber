package com.example.timebarteryeah.views.tab.module

import com.example.timebarteryeah.contracts.MyPostContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.MyPostPresenter
import com.example.timebarteryeah.views.tab.MyPostTab
import dagger.Binds
import dagger.Module

@Module
abstract class MyPostModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(myPostTab: MyPostTab): MyPostContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(myPostPresenter: MyPostPresenter): MyPostContract.Presenter

}
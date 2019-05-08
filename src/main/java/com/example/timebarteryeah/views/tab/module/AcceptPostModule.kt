package com.example.timebarteryeah.views.tab.module

import com.example.timebarteryeah.contracts.AcceptPostContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.AcceptPostPresenter
import com.example.timebarteryeah.views.tab.AcceptPostTab
import dagger.Binds
import dagger.Module

@Module
abstract class AcceptPostModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(acceptPostTab: AcceptPostTab): AcceptPostContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(acceptPostPresenter: AcceptPostPresenter): AcceptPostContract.Presenter

}
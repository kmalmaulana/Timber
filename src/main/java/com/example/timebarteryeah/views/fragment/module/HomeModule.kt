package com.example.timebarteryeah.views.fragment.module

import com.example.timebarteryeah.contracts.HomeContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.HomePresenter
import com.example.timebarteryeah.views.fragment.HomeFragment
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(homeFragment: HomeFragment): HomeContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(homePresenter: HomePresenter): HomeContract.Presenter

}
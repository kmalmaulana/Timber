package com.example.timebarteryeah.views.activity.module

import com.example.timebarteryeah.contracts.SignInContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.SignInPresenter
import com.example.timebarteryeah.views.activity.SignInActivity
import dagger.Binds
import dagger.Module

@Module
abstract class SignInModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(signInActivity: SignInActivity): SignInContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(signInPresenter: SignInPresenter): SignInContract.Presenter

}
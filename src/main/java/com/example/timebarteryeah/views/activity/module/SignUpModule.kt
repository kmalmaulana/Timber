package com.example.timebarteryeah.views.activity.module

import com.example.timebarteryeah.contracts.SignUpContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.SignUpPresenter
import com.example.timebarteryeah.views.activity.SignUpActivity
import dagger.Binds
import dagger.Module

@Module
abstract class SignUpModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(signUpActivity: SignUpActivity): SignUpContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(signUpPresenter: SignUpPresenter): SignUpContract.Presenter

}
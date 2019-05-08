package com.example.timebarteryeah.views.fragment.module

import com.example.timebarteryeah.contracts.AddPostContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.AddPostPresenter
import com.example.timebarteryeah.views.fragment.AddPostFragment
import dagger.Binds
import dagger.Module

@Module
abstract class AddPostModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(addPostFragment: AddPostFragment): AddPostContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(addPostPresenter: AddPostPresenter): AddPostContract.Presenter

}
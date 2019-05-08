package com.example.timebarteryeah.views.fragment.module

import com.example.timebarteryeah.contracts.MyProfileContract
import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.presenters.MyProfilePresenter
import com.example.timebarteryeah.views.fragment.MyProfileFragment
import dagger.Binds
import dagger.Module

@Module
abstract class MyProfileModule {

    @ActivityScope
    @Binds
    internal abstract fun provideView(myProfileFragment: MyProfileFragment): MyProfileContract.View

    @ActivityScope
    @Binds
    internal abstract fun providePresenter(myProfilePresenter: MyProfilePresenter): MyProfileContract.Presenter

}
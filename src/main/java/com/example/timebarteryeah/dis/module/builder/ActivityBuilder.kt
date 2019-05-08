package com.example.timebarteryeah.dis.module.builder

import com.example.timebarteryeah.dis.scope.ActivityScope
import com.example.timebarteryeah.views.activity.DetailPostActivity
import com.example.timebarteryeah.views.activity.SignInActivity
import com.example.timebarteryeah.views.activity.SignUpActivity
import com.example.timebarteryeah.views.activity.module.DetailPostModule
import com.example.timebarteryeah.views.activity.module.SignInModule
import com.example.timebarteryeah.views.activity.module.SignUpModule
import com.example.timebarteryeah.views.fragment.AddPostFragment
import com.example.timebarteryeah.views.fragment.HomeFragment
import com.example.timebarteryeah.views.fragment.LeaderBoardFragment
import com.example.timebarteryeah.views.fragment.MyProfileFragment
import com.example.timebarteryeah.views.fragment.module.AddPostModule
import com.example.timebarteryeah.views.fragment.module.HomeModule
import com.example.timebarteryeah.views.fragment.module.LeaderBoardModule
import com.example.timebarteryeah.views.fragment.module.MyProfileModule
import com.example.timebarteryeah.views.tab.AcceptPostTab
import com.example.timebarteryeah.views.tab.MyPostTab
import com.example.timebarteryeah.views.tab.module.AcceptPostModule
import com.example.timebarteryeah.views.tab.module.MyPostModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SignUpModule::class])
    internal abstract fun provideSignUpActivity(): SignUpActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SignInModule::class])
    internal abstract fun provideSignInActivity(): SignInActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MyProfileModule::class])
    internal abstract fun provideMyProfileFragment(): MyProfileFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [AddPostModule::class])
    internal abstract fun provideAddPostFragment(): AddPostFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun provideHomeFragment(): HomeFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailPostModule::class])
    internal abstract fun provideDetailPostActivity(): DetailPostActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MyPostModule::class])
    internal abstract fun provideMyPostTab(): MyPostTab

    @ActivityScope
    @ContributesAndroidInjector(modules = [AcceptPostModule::class])
    internal abstract fun provideAcceptPostTab(): AcceptPostTab

    @ActivityScope
    @ContributesAndroidInjector(modules = [LeaderBoardModule::class])
    internal abstract fun provideLeaderBoardFragment(): LeaderBoardFragment

}
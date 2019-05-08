package com.example.timebarteryeah.dis.component

import android.app.Application
import com.example.timebarteryeah.dis.App
import com.example.timebarteryeah.dis.module.AppModule
import com.example.timebarteryeah.dis.module.builder.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent

    }

}
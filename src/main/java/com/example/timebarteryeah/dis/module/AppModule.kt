package com.example.timebarteryeah.dis.module

import android.app.Application
import com.example.timebarteryeah.dis.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module(includes = [FirebaseModule::class])
class AppModule {

    @ApplicationScope
    @Provides
    internal fun provideApplication(application: Application): Application =
            application

}
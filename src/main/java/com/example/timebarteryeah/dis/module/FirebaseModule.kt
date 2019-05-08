package com.example.timebarteryeah.dis.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class FirebaseModule {

    @Provides
    @Reusable
    internal fun provideAuth(): FirebaseAuth =
            FirebaseAuth.getInstance()

    @Provides
    @Reusable
    internal fun provideFirestore(): FirebaseFirestore =
            FirebaseFirestore.getInstance()

    @Provides
    @Reusable
    internal fun provideStorage(): StorageReference =
            FirebaseStorage.getInstance().reference

}
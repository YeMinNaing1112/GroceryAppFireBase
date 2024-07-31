package com.yeminnaing.firebasecomposeproject.presentationLayer.di

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.yeminnaing.firebasecomposeproject.dataLayer.network.RealTimeDataBaseImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.repositories.GetDataFromFireBaseRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFireBaseData():DatabaseReference{
        return Firebase.database.reference
    }

    @Provides
    @Singleton
    fun provideRealTimeDataBaseImpl(databaseReference: DatabaseReference): RealTimeDataBaseImpl {
        return com.yeminnaing.firebasecomposeproject.dataLayer.network.RealTimeDataBaseImpl(
            databaseReference
        )
    }


}
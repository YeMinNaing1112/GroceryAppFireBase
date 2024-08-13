package com.yeminnaing.firebasecomposeproject.presentationLayer.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.yeminnaing.firebasecomposeproject.dataLayer.network.CloudFireStoreImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.network.RealTimeDataBaseImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.network.auth.FireBaseAuthManager
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
    fun provideRealTimeDataBaseReference():DatabaseReference{
        return Firebase.database.reference
    }

    @Provides
    @Singleton
    fun provideRealTimeDataBaseImpl(databaseReference: DatabaseReference): RealTimeDataBaseImpl {
        return com.yeminnaing.firebasecomposeproject.dataLayer.network.RealTimeDataBaseImpl(
            databaseReference
        )
    }

    @Provides
    @Singleton
    fun provideFireStoreReference(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideFireStoreImpl(fireStoreReference:FirebaseFirestore):CloudFireStoreImpl{
        return CloudFireStoreImpl(fireStoreReference)
    }


    @Provides
    @Singleton
    fun provideFireBaseAuthReference(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireBaseAuthManager(mFireBaseAuth:FirebaseAuth):FireBaseAuthManager{
        return FireBaseAuthManager(mFireBaseAuth)
    }

}
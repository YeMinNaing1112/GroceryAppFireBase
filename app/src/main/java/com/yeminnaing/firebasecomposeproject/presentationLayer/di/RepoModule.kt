package com.yeminnaing.firebasecomposeproject.presentationLayer.di

import com.yeminnaing.firebasecomposeproject.dataLayer.repositories.GroceryModelRepoImpl
import com.yeminnaing.firebasecomposeproject.domainLayer.repositories.GroceryModelRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindingGetDataFromFireBaseRepoImpl
                (groceryModelRepoImpl: GroceryModelRepoImpl): GroceryModelRepo

}
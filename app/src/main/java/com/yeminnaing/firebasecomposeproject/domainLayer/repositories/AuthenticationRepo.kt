package com.yeminnaing.firebasecomposeproject.domainLayer.repositories

interface AuthenticationRepo {
    fun login(email:String,password:String,onSuccess:()->Unit,onFailure:(String)->Unit)
    fun register(email:String,password:String,userName:String,onSuccess:()->Unit,onFailure:(String)->Unit)
    fun getUserName():String

    fun checkAuthStatus():Boolean

    fun signOut()
}
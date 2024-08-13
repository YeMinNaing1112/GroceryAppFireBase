package com.yeminnaing.firebasecomposeproject.dataLayer.network.auth

interface AuthManager {
    fun login(email:String,password:String,onSuccess:()->Unit,onFailure:(String)->Unit)
    fun register(email:String,password:String,userName:String,onSuccess:()->Unit,onFailure:(String)->Unit)
    fun getUserName():String

    fun checkAuthStatus():Boolean

    fun signOut()
}
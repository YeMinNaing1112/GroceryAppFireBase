package com.yeminnaing.firebasecomposeproject.presentationLayer.authScreen

import androidx.lifecycle.ViewModel
import com.google.android.play.integrity.internal.o
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.FireBaseAnalyticManager
import com.yeminnaing.firebasecomposeproject.domainLayer.repositories.AuthenticationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthenticationVm @Inject constructor(
    private val authenticationRepo: AuthenticationRepo,
):ViewModel() {
    private var _authState= MutableStateFlow<AuthState>(AuthState.Empty)
    val authState=_authState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (authenticationRepo.checkAuthStatus()){
            _authState.value=AuthState.Authenticated
        }else{
            _authState.value=AuthState.Unauthenticated
        }
    }
    fun logIn(email:String,password:String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value=AuthState.Loading
        authenticationRepo.login(
            email=email,
            password=password,
            onSuccess = {
                _authState.value=AuthState.Authenticated
            }, onFailure = {
               _authState.value=AuthState.Error(it)
            }
        )
    }

    fun signIn(name:String,email:String,password: String){
        if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        authenticationRepo.register(
            email=email,
            password=password,
            userName = name,
            onSuccess = {
                _authState.value=AuthState.Authenticated
            }, onFailure = {
                _authState.value=AuthState.Error(it)
            }
        )
    }
    fun signOut(){
     authenticationRepo.signOut()
        _authState.value=AuthState.Unauthenticated
    }


}

sealed class AuthState{
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data object Empty : AuthState()
    data class Error(val message : String) : AuthState()
}
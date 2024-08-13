package com.yeminnaing.firebasecomposeproject.dataLayer.repositories

import com.yeminnaing.firebasecomposeproject.dataLayer.network.auth.FireBaseAuthManager
import com.yeminnaing.firebasecomposeproject.domainLayer.repositories.AuthenticationRepo
import javax.inject.Inject

class AuthenticationRepoImpl @Inject constructor(
    private val mFireBaseAuthManager: FireBaseAuthManager,
) : AuthenticationRepo {
    override fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        mFireBaseAuthManager.login(
            email, password, onSuccess, onFailure
        )
    }

    override fun register(
        email: String,
        password: String,
        userName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        mFireBaseAuthManager.register(email, password, userName, onSuccess, onFailure)
    }

    override fun getUserName(): String {
        return mFireBaseAuthManager.getUserName()
    }

    override fun checkAuthStatus(): Boolean {
        return mFireBaseAuthManager.checkAuthStatus()
    }

    override fun signOut() {
           mFireBaseAuthManager.signOut()
    }
}
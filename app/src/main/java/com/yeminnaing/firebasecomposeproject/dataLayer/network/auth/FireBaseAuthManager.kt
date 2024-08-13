package com.yeminnaing.firebasecomposeproject.dataLayer.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class FireBaseAuthManager @Inject constructor(
    private val mFireBaseAuth: FirebaseAuth,
) : AuthManager {
    override fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
       mFireBaseAuth.signInWithEmailAndPassword(email,password)
           .addOnCompleteListener{
               if (it.isSuccessful && it.isComplete){
                   onSuccess()
               }else{
                   onFailure(it.exception?.message?:"Please Check The Internet Connection")
               }
           }
    }

    override fun register(
        email: String,
        password: String,
        userName:String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        mFireBaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful && it.isComplete){
                    mFireBaseAuth.currentUser?.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(userName).build()
                    )
                   onSuccess()
                }else{
                    onFailure(it.exception?.message?:"Please Check Internet Connection")
                }
            }
    }

    override fun getUserName(): String {
      return  mFireBaseAuth.currentUser?.displayName ?: ""
    }

    override fun checkAuthStatus(): Boolean {
        return mFireBaseAuth.currentUser != null
    }

    override fun signOut() {
        mFireBaseAuth.signOut()
    }
}
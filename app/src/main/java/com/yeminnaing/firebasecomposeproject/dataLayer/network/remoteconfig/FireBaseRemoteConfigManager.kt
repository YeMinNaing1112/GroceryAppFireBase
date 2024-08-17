package com.yeminnaing.firebasecomposeproject.dataLayer.network.remoteconfig

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import javax.inject.Inject

class FireBaseRemoteConfigManager @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) {
    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun setUpConfigWithDefaultValues() {
        val defaultValues: Map<String, Any> = hashMapOf(
            "mainScreenAppBarTitle" to "Grocery-App"
        )
        firebaseRemoteConfig.setDefaultsAsync(defaultValues)
    }

    fun fetchRemoteConfigs() {
        firebaseRemoteConfig.fetch()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Firebase Remote Config fetch success")
                    firebaseRemoteConfig.activate().addOnCompleteListener {
                        Log.d("Firebase", "Firebase Remote Config activated")
                    }
                } else {
                    Log.d("Firebase", "Firebase Remote Config fetch failed")
                }

            }
    }

    fun getName():String{
        return firebaseRemoteConfig.getValue("mainScreenAppBarTitle").asString()
    }
}
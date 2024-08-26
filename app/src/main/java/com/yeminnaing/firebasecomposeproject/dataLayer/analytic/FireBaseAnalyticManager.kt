package com.yeminnaing.firebasecomposeproject.dataLayer.analytic

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FireBaseAnalyticManager {
    fun sendEventsToFireBaseAnalytic(
        context:Context,
        eventName:String,
        key:String="",
        value:String=""
    ){
        if (key.isNotEmpty()&& value.isNotEmpty() ){
           FirebaseAnalytics.getInstance(context).logEvent(eventName,buildBundle(key,value))

        }else{
            FirebaseAnalytics.getInstance(context).logEvent(eventName,null)
        }

    }

    private fun buildBundle(key: String,value: String):Bundle{
        val bundle= Bundle()
        bundle.putString(key,value)
        return bundle
    }

}
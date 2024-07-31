package com.yeminnaing.firebasecomposeproject.dataLayer.network

import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import com.yeminnaing.firebasecomposeproject.domainLayer.Resources

interface FireBaseApi {
    fun getGroceries(onSuccess:  (groceries:List<GroceryResponse>)->Unit,onFialure:(String)->Unit)
    fun addGroceries(name:String, description:String,amount:Int)
     fun deleteGrocery(name: String)
}
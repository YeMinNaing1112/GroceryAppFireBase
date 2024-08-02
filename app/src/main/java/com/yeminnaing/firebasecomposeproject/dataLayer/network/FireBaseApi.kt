package com.yeminnaing.firebasecomposeproject.dataLayer.network

import android.content.Context
import android.net.Uri
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse

interface FireBaseApi {
    fun getGroceries(onSuccess:  (groceries:List<GroceryResponse>)->Unit,onFialure:(String)->Unit)
    fun addGroceries(name:String, description:String,amount:Int,image: String)
     fun deleteGrocery(name: String)

     fun upLoadImage(image: Uri, groceryResponse: GroceryResponse,context:Context)
}
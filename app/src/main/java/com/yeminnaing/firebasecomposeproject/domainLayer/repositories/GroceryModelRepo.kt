package com.yeminnaing.firebasecomposeproject.domainLayer.repositories

import android.content.Context
import android.net.Uri
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse

interface GroceryModelRepo {
     fun getData(
        onSuccess: (groceries: List<GroceryResponse>) -> Unit,
        onFialure: (String) -> Unit,
    )
     fun addGroceries(
         name:String,
         description:String,
         amount:Int,
         image:String
     )

     fun removeValue(
         name:String,
     )
    fun upLoadImage(image: Uri, groceryResponse: GroceryResponse,context: Context)
}
package com.yeminnaing.firebasecomposeproject.domainLayer.repositories

import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import com.yeminnaing.firebasecomposeproject.domainLayer.Resources
import com.yeminnaing.firebasecomposeproject.domainLayer.response.GroceryModel

interface GetDataFromFireBaseRepo {
     fun getData(
        onSuccess: (groceries: List<GroceryResponse>) -> Unit,
        onFialure: (String) -> Unit,
    )
     fun addGroceries(
         name:String,
         description:String,
         amount:Int
     )

     fun removeValue(
         name:String,
     )
}
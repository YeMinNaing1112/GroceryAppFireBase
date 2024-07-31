package com.yeminnaing.firebasecomposeproject.dataLayer.repositories

import com.yeminnaing.firebasecomposeproject.dataLayer.network.RealTimeDataBaseImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import com.yeminnaing.firebasecomposeproject.domainLayer.Resources
import com.yeminnaing.firebasecomposeproject.domainLayer.repositories.GetDataFromFireBaseRepo
import com.yeminnaing.firebasecomposeproject.domainLayer.response.GroceryModel
import javax.inject.Inject

class GetDataFromFireBaseRepoImpl @Inject constructor (
    private val realtimeDataBase:RealTimeDataBaseImpl
):GetDataFromFireBaseRepo {


    override  fun getData(
        onSuccess: (groceries: List<GroceryResponse>) -> Unit,
        onFialure: (String) -> Unit,
    ) {
       realtimeDataBase.getGroceries(
           onSuccess=onSuccess,
           onFialure=onFialure
       )
    }

    override fun addGroceries(name: String, description: String, amount: Int) {
        realtimeDataBase.addGroceries(name, description, amount)
    }

    override fun removeValue(name: String) {
        realtimeDataBase.deleteGrocery(name)
    }
}
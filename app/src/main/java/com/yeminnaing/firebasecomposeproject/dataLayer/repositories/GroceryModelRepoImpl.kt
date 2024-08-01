package com.yeminnaing.firebasecomposeproject.dataLayer.repositories

import com.yeminnaing.firebasecomposeproject.dataLayer.network.CloudFireStoreImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.network.RealTimeDataBaseImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import com.yeminnaing.firebasecomposeproject.domainLayer.repositories.GroceryModelRepo
import javax.inject.Inject

class GroceryModelRepoImpl @Inject constructor (
//    private val realtimeDataBase:RealTimeDataBaseImpl
    private val cloudFireStore:CloudFireStoreImpl
):GroceryModelRepo {


    override  fun getData(
        onSuccess: (groceries: List<GroceryResponse>) -> Unit,
        onFialure: (String) -> Unit,
    ) {
//       realtimeDataBase.getGroceries(
//           onSuccess=onSuccess,
//           onFialure=onFialure
//       )
        cloudFireStore.getGroceries(
            onSuccess=onSuccess,
            onFialure=onFialure
        )
    }

    override fun addGroceries(name: String, description: String, amount: Int) {
//        realtimeDataBase.addGroceries(name, description, amount)
        cloudFireStore.addGroceries(name, description, amount)
    }

    override fun removeValue(name: String) {
//        realtimeDataBase.deleteGrocery(name)
        cloudFireStore.deleteGrocery(name)
    }
}
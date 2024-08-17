package com.yeminnaing.firebasecomposeproject.dataLayer.repositories

import android.content.Context
import android.net.Uri
import com.yeminnaing.firebasecomposeproject.dataLayer.network.CloudFireStoreImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.network.RealTimeDataBaseImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.network.remoteconfig.FireBaseRemoteConfigManager
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import com.yeminnaing.firebasecomposeproject.domainLayer.repositories.GroceryModelRepo
import javax.inject.Inject

class GroceryModelRepoImpl @Inject constructor (
//    private val realtimeDataBase:RealTimeDataBaseImpl
    private val cloudFireStore: CloudFireStoreImpl,
    private val fireBaseRemoteConfigManager: FireBaseRemoteConfigManager
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

    override fun addGroceries(name: String, description: String, amount: Int,image: String) {
//        realtimeDataBase.addGroceries(name, description, amount,image)
        cloudFireStore.addGroceries(name, description, amount,image)
    }

    override fun removeValue(name: String) {
//        realtimeDataBase.deleteGrocery(name)
        cloudFireStore.deleteGrocery(name)
    }

    override fun upLoadImage(image: Uri, groceryResponse: GroceryResponse,context: Context) {
//        realtimeDataBase.upLoadImage(image, groceryResponse,context)
        cloudFireStore.upLoadImage(image, groceryResponse, context)
    }

    override fun setUpRemoteConfigWithDefaultValues() {
        fireBaseRemoteConfigManager.setUpConfigWithDefaultValues()
    }

    override fun fetchRemoteConfigs() {
      fireBaseRemoteConfigManager.fetchRemoteConfigs()
    }

    override fun getAppNameFromRemoteConfig(): String {
      return fireBaseRemoteConfigManager.getName()
    }
}
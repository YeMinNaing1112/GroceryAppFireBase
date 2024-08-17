package com.yeminnaing.firebasecomposeproject.presentationLayer

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.yeminnaing.firebasecomposeproject.dataLayer.repositories.GroceryModelRepoImpl
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GroceryScreenVm @Inject constructor(
    private val repoImpl: GroceryModelRepoImpl,
) : ViewModel() {
    val _getDataState = MutableStateFlow<GetDataState>(GetDataState.Empty)
    val getDataState = _getDataState.asStateFlow()

    init {
        getData()
        setUpRemoteWithDefaultValues()
        fetchRemoteConfig()


    }

    fun getName():String {
        return repoImpl.getAppNameFromRemoteConfig()
    }

    private fun setUpRemoteWithDefaultValues() {
        repoImpl.setUpRemoteConfigWithDefaultValues()
    }

    private fun fetchRemoteConfig() {
        repoImpl.fetchRemoteConfigs()
    }


    private fun getData() {
        _getDataState.value=GetDataState.Loading
        repoImpl.getData(
            onSuccess = {
                _getDataState.value = GetDataState.Success(it)
                Log.d("ABCD", "getData: $it ")
            },
            onFialure = {
                _getDataState.value = GetDataState.Error(it)
            }
        )
    }

    fun addData(name:String,description:String,amount:Int,image:String){
        repoImpl.addGroceries(name, description, amount,image)
    }


    fun removeValue(name:String){
        repoImpl.removeValue(name)
    }

    fun upLoadImage(image: Uri, groceryResponse: GroceryResponse,context: Context){
        repoImpl.upLoadImage(image,groceryResponse,context)
    }

    sealed interface GetDataState {
        data object Empty : GetDataState
        data object Loading:GetDataState
        data class Success(val data: List<GroceryResponse>) : GetDataState
        data class Error(val error: String) : GetDataState
    }
}


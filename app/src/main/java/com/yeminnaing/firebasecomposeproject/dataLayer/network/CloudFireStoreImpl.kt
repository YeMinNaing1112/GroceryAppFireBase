package com.yeminnaing.firebasecomposeproject.dataLayer.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import javax.inject.Inject

class CloudFireStoreImpl @Inject constructor(
    private val db: FirebaseFirestore,
) : FireBaseApi {

//    private val storage = FirebaseStorage.getInstance()
//    private val storageReference = storage.reference
    override fun getGroceries(
        onSuccess: (groceries: List<GroceryResponse>) -> Unit,
        onFialure: (String) -> Unit,
    ) {
        db.collection("groceries")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFialure(it.message ?: "Please Check connection")
                } ?: run {
                    val groceryList = arrayListOf<GroceryResponse>()
                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data
                        val grocery = GroceryResponse()
                        grocery.name=data?.get("name") as String
                        grocery.description= data["description"] as String
                        grocery.amount=(data["amount"] as Number).toInt()
                        groceryList.add(grocery)
                    }
                    onSuccess(groceryList)
                }

            }
    }

    override fun addGroceries(name: String, description: String, amount: Int) {
         val groceryMap= hashMapOf(
             "name" to name,
             "description" to description,
             "amount" to amount.toLong()
         )

        db.collection("groceries")
            .document(name)
            .set(groceryMap)
            .addOnSuccessListener { Log.d("Success", "SuccessFully Add Data") }
            .addOnFailureListener{Log.d("Fail", "Fail to Add Data") }
    }

    override fun deleteGrocery(name: String) {
        db.collection("groceries")
            .document(name)
            .delete()
            .addOnSuccessListener { Log.d("Success", "SuccessFully Delete Data") }
            .addOnFailureListener{Log.d("Fail", "Fail to Delete Data") }
    }
}
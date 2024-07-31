package com.yeminnaing.firebasecomposeproject.dataLayer.network

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import javax.inject.Inject

class RealTimeDataBaseImpl @Inject constructor(
    private val dataBase:DatabaseReference
) :FireBaseApi {


    override fun getGroceries(
        onSuccess: (groceries: List<GroceryResponse>) -> Unit,
        onFialure: (String) -> Unit,
    ) {
        dataBase.child("groceries").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val groceryList = arrayListOf<GroceryResponse>()
                snapshot.children.forEach { dataSnapshot ->
                    dataSnapshot.getValue(GroceryResponse::class.java)?.let {
                        groceryList.add(it)
                    }
                }
                onSuccess(groceryList)
            }

            override fun onCancelled(error: DatabaseError) {
                onFialure(error.message)
            }
        })
    }

    override fun addGroceries(name: String, description: String, amount: Int) {
        dataBase.child("groceries").child(name).setValue(GroceryResponse(name, description, amount))
    }

    override fun deleteGrocery(name: String) {
        dataBase.child("groceries").child(name).removeValue()
    }
}
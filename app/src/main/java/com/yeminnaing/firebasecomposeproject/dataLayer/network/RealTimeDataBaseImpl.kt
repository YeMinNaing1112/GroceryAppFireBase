package com.yeminnaing.firebasecomposeproject.dataLayer.network

import android.content.Context
import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import java.util.UUID
import javax.inject.Inject

class RealTimeDataBaseImpl @Inject constructor(
    private val dataBase:DatabaseReference
) :FireBaseApi {

        private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
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

    override fun addGroceries(name: String, description: String, amount: Int,image: String) {
        dataBase.child("groceries").child(name).setValue(GroceryResponse(name, description, amount,image))
    }

    override fun deleteGrocery(name: String) {
        dataBase.child("groceries").child(name).removeValue()
    }

    override fun upLoadImage(image: Uri, groceryResponse: GroceryResponse,context: Context) {


        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val byteArray:ByteArray?=context.contentResolver
            .openInputStream(image)
            ?.use {
                it.readBytes()
            }

        val uploadTask = byteArray?.let { imageRef.putBytes(it) }
        uploadTask?.addOnFailureListener {
            //
        }?.addOnSuccessListener { taskSnapshot ->
            //
        }

        val urlTask = uploadTask?.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }?.addOnCompleteListener { task ->
            val imageUrl = task.result?.toString()
            addGroceries(groceryResponse.name ?: "", groceryResponse.description ?: "", groceryResponse.amount ?: 0, imageUrl ?: "")
        }
    }
    }

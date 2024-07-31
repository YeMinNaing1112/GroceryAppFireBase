package com.yeminnaing.firebasecomposeproject.presentationLayer

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import com.yeminnaing.firebasecomposeproject.domainLayer.response.GroceryModel

@Composable
fun GroceryScreen(modifier:Modifier=Modifier) {

    val viewModel: GroceryScreenVm = hiltViewModel()
    val dataState by viewModel.getDataState.collectAsState()
    var showDialogState by remember { mutableStateOf(false) }
    var groceryName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick ={showDialogState=true} ) {
                Text(text = "+")
            }
        }
    ) { paddingValue->
        GroceryScreenDesign(modifier = Modifier,dataState,paddingValue, remove = {name->
            viewModel.removeValue(name)
        }, edit = {
            showDialogState=true
            groceryName=it.name.toString()
            description=it.description.toString()
            amount=it.amount.toString()
        })
        if (showDialogState) {
            Dialog(onDismissRequest = { showDialogState=false}) {
                Surface(
                    modifier = modifier
                        .padding(16.dp)
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                ) {
                    Column(modifier= modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                        TextField(
                            modifier = modifier.padding(top = 16.dp),
                            value = groceryName,
                            onValueChange = {name-> groceryName=name},
                            placeholder = { Text("GroceryName") },
                        )

                        TextField(
                            modifier = modifier.padding(top = 16.dp),
                            value = description,
                            onValueChange = {name-> description=name},
                            placeholder = { Text("Description") },
                        )

                        TextField(
                            modifier = modifier.padding(top = 16.dp),
                            value = amount,
                            onValueChange = {name-> amount=name},
                            placeholder = { Text("Amount") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Button(onClick = {showDialogState=false
                        viewModel.addData(name=groceryName,description=description, amount = amount.toInt())
                            groceryName=""
                            description=""
                            amount=""
                        }) {
                            Text(text = "Save")

                        }


                    }

                }
            }
        }
    }

}

@Composable
fun GroceryScreenDesign(
    modifier: Modifier = Modifier,
    dataState: GroceryScreenVm.GetDataState,
    paddingValue: PaddingValues,
    remove:(name:String)->Unit,
    edit:(groceryResponse :GroceryResponse)->Unit
) {

    when(dataState){
        GroceryScreenVm.GetDataState.Empty ->{
             Text(
                 text = "Empty",
                 modifier = modifier
                     .fillMaxSize()
                  , fontSize = 20.sp

             )
        }
        is GroceryScreenVm.GetDataState.Error -> {
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
        }
        is GroceryScreenVm.GetDataState.Success -> {

            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(dataState.data) { groceryResponse ->
                    GroceryItemCard(groceryResponse,remove={
                        groceryResponse.name?.let { remove(it) }
                    }, edit = {
                        edit(groceryResponse)
                    })
                }

            }
        }

        GroceryScreenVm.GetDataState.Loading ->{
            Box(modifier = modifier.fillMaxSize()){
               Text(text = "Loading.....", modifier = modifier.align(Alignment.Center))
            }
        }
    }

}

@Composable
fun GroceryItemCard(groceryModel: GroceryResponse, modifier: Modifier = Modifier,remove:()->Unit,edit:()->Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                groceryModel.name?.let { Text(text = it) }
                Text(text = groceryModel.amount.toString())

            }
            Spacer(modifier = modifier.height(16.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                groceryModel.description?.let { Text(text = it) }

                Row {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "",modifier=modifier.clickable {
                      edit()
                    })
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "", modifier = modifier.clickable {
                        remove()
                    })
                }

            }
        }
    }
}

@Preview
@Composable
fun GroceryItemCardPreview() {
    GroceryItemCard(
        groceryModel = GroceryResponse(
            amount = 5, description = "French fires to go with pizza", name = "Fries"
        ), remove = {},
        edit = {}
    )
}

@Preview
@Composable
fun GroceryScreenDesignPrev() {
    GroceryScreenDesign(
        modifier=Modifier,
        dataState = GroceryScreenVm.GetDataState.Empty,
        paddingValue = PaddingValues(20.dp),
        remove = {},
        edit = {}
    )
}

//region dummy
val groceryList = listOf<GroceryModel>(
    GroceryModel(
        amount = 5, description = "French fires to go with pizza", name = "Fries"
    ),
    GroceryModel(
        amount = 4, description = "French fires to go with pizza", name = "Pizza"
    ),
    GroceryModel(
        amount = 3, description = "French fires to go with pizza", name = "Milk"
    ),

    )

//endregion
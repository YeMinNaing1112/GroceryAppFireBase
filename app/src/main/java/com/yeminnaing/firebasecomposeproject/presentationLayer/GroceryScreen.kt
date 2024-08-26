package com.yeminnaing.firebasecomposeproject.presentationLayer

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import com.yeminnaing.firebasecomposeproject.R
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.FireBaseAnalyticManager
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.PARAMETER_EMAIL
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.SCREEN_HOME
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.SCREEN_LOGIN
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.TAP_ADD_GROCERY
import com.yeminnaing.firebasecomposeproject.dataLayer.analytic.TAP_LOGIN
import com.yeminnaing.firebasecomposeproject.dataLayer.response.GroceryResponse
import com.yeminnaing.firebasecomposeproject.domainLayer.response.GroceryModel
import com.yeminnaing.firebasecomposeproject.presentationLayer.authScreen.AuthenticationVm
import com.yeminnaing.firebasecomposeproject.presentationLayer.navigation.GroceryAppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryScreen(modifier: Modifier = Modifier,navController: NavController) {

    val viewModel: GroceryScreenVm = hiltViewModel()
    val authViewModel:AuthenticationVm= hiltViewModel()
    val dataState by viewModel.getDataState.collectAsState()
    var showDialogState by remember { mutableStateOf(false) }
    var groceryName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val context= LocalContext.current

    val fireBaseAnalyticManager= FireBaseAnalyticManager()

    LaunchedEffect(Unit){
        fireBaseAnalyticManager.sendEventsToFireBaseAnalytic(context, SCREEN_HOME)
    }


    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia() ,
        onResult = {uri ->
            selectedImageUri =uri
        })

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text =viewModel.getName() )
            })

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                fireBaseAnalyticManager.sendEventsToFireBaseAnalytic(context, TAP_ADD_GROCERY)

                showDialogState = true }) {
                Text(text = "+")
            }
        },

    ) { paddingValue ->
        GroceryScreenDesign(modifier = Modifier, dataState, paddingValue, remove = { name ->
            viewModel.removeValue(name)
        }, edit = {
            showDialogState = true
            groceryName = it.name.toString()
            description = it.description.toString()
            amount = it.amount.toString()
        }, addPhoto = {
           photoPickerLauncher.launch(
               PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
           )
            selectedImageUri?.let { uri -> viewModel.upLoadImage(image = uri, groceryResponse = it, context = context) }

        }, signOut = {
            authViewModel.signOut()
            navController.navigate(GroceryAppScreens.LogInScreen){
                popUpTo(navController.graph.findStartDestination().id){
                    saveState=false
                    inclusive=true
                }
                launchSingleTop=true
            }
        })
        if (showDialogState) {
            Dialog(onDismissRequest = { showDialogState = false }) {
                Surface(
                    modifier = modifier
                        .padding(16.dp)
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Min)
                ) {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        TextField(
                            modifier = modifier.padding(top = 16.dp),
                            value = groceryName,
                            onValueChange = { name -> groceryName = name },
                            placeholder = { Text("GroceryName") },
                        )

                        TextField(
                            modifier = modifier.padding(top = 16.dp),
                            value = description,
                            onValueChange = { name -> description = name },
                            placeholder = { Text("Description") },
                        )

                        TextField(
                            modifier = modifier.padding(top = 16.dp),
                            value = amount,
                            onValueChange = { name -> amount = name },
                            placeholder = { Text("Amount") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        AsyncImage(model = selectedImageUri, contentDescription = "",
                            modifier
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally))
                        Button(onClick = {
                            showDialogState = false
                            selectedImageUri?.let { uri ->
                                viewModel.
                                upLoadImage(image = uri, groceryResponse = GroceryResponse(
                                    name = groceryName,
                                    description =description,
                                    amount =amount.toInt()
                                ), context = context) }
                            groceryName = ""
                            description = ""
                            amount = ""
                        },modifier.padding(end = 16.dp)) {
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
    remove: (name: String) -> Unit,
    edit: (groceryResponse: GroceryResponse) -> Unit,
    addPhoto: (groceryResponse: GroceryResponse) -> Unit,
    signOut:()-> Unit
) {
    Column {
        Button(onClick = { signOut()}) {
            Text(text = "SignOut")

        }
        when (dataState) {
            GroceryScreenVm.GetDataState.Empty -> {
                Text(
                    text = "Empty",
                    modifier = modifier
                        .fillMaxSize(), fontSize = 20.sp

                )
            }

            is GroceryScreenVm.GetDataState.Error -> {
                Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            }

            is GroceryScreenVm.GetDataState.Success -> {

                LazyColumn(modifier = modifier.fillMaxWidth()) {
                    items(dataState.data) { groceryResponse ->
                        GroceryItemCard(groceryResponse, remove = {
                            groceryResponse.name?.let { remove(it) }
                        }, edit = {
                            edit(groceryResponse)
                        }, addPhoto = {
                            addPhoto(groceryResponse)
                        })
                    }

                }
            }

            GroceryScreenVm.GetDataState.Loading -> {
                Box(modifier = modifier.fillMaxSize()) {
                    Text(text = "Loading.....", modifier = modifier.align(Alignment.Center))
                }
            }
        }

    }

}

@Composable
fun GroceryItemCard(
    groceryModel: GroceryResponse,
    modifier: Modifier = Modifier,
    remove: () -> Unit,
    edit: () -> Unit,
    addPhoto:()->Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {

        Row(modifier = modifier.padding(16.dp)) {
            AsyncImage(
                model = groceryModel.image, contentDescription = "",
                modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Column {
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
                        Image(
                            painter = painterResource(R.drawable.baseline_cloud_upload_24),
                            contentDescription = "",
                            modifier=modifier.clickable {
                                addPhoto()
                            }
                        )

                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "",
                            modifier = modifier.clickable {
                                edit()
                            })
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "",
                            modifier = modifier.clickable {
                                remove()
                            })
                    }

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
        edit = {},
        addPhoto = {},
    )
}

@Preview
@Composable
fun GroceryScreenDesignPrev() {
    GroceryScreenDesign(
        modifier = Modifier,
        dataState = GroceryScreenVm.GetDataState.Empty,
        paddingValue = PaddingValues(20.dp),
        remove = {},
        edit = {},
        addPhoto = {},
        signOut = {}
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
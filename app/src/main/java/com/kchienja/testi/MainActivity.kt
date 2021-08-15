package com.kchienja.testi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import com.kchienja.testi.model.RepositoriesModel
import com.kchienja.testi.model.ResultData
import com.kchienja.testi.ui.theme.TestiTheme
import com.kchienja.testi.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestiTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ReposData()
                }
            }
        }
    }
}

@Composable
fun ReposData(){
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val dataState = mainActivityViewModel.repositoriesListLiveData.observeAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        when(val resultData = dataState.value){
            is ResultData.Loading -> {
                CircularProgressIndicator(
                    strokeWidth = 1.dp,
                    color = Color.Blue
                )
            }
            is ResultData.Success -> {
                val data = resultData.data

                Log.d("DATA::", data.toString())
                Text(text = "Data Loaded")

            }
            is ResultData.Failed -> {
                Text(text = "Failed To Load Data")
            }
            is ResultData.Exception -> {
                Text(text = "Something went wrong. Please try again!")
            }

        }

    }

}

@Composable
fun RepoList(repositoriesModel: RepositoriesModel){
    LazyColumn{
        if (!repositoriesModel.isNullOrEmpty()){
            itemsIndexed(repositoriesModel){index, item ->  ReposListItem(item = item)}
        }
    }
}

@Composable
fun ReposListItem(item: RepositoriesModel.RepositoriesModelItem){
    
    Card() {
        Text(text = "${item.fullName}")
    }
    
}
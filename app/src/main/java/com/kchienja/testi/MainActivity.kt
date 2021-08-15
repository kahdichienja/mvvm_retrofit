package com.kchienja.testi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
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
//                Text(text = "Data Loaded")

                RepoList(repositoriesModel = data)
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
fun RepoList(repositoriesModel: RepositoriesModel?){
    LazyColumn{
        if (!repositoriesModel.isNullOrEmpty()){
            itemsIndexed(repositoriesModel){index, item ->  ReposListItem(item = item)}
        }
    }
}

@Composable
fun ReposListItem(modifier: Modifier = Modifier, item: RepositoriesModel.RepositoriesModelItem){
    
    Card(shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(10.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(item.owner?.avatarUrl),
                    contentDescription = null,
                    modifier = Modifier.size(size = 20.dp)
                )
                Spacer(modifier = Modifier.size(height = 0.dp, width = 5.dp))
                Text(text = item.fullName?.substringBefore(delimiter = "/").toString(), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = item.name.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = item.description.toString(), fontSize = 14.sp)
        }

    }
    
}
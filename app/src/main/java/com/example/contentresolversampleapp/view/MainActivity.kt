package com.example.contentresolversampleapp.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contentresolversampleapp.model.RandomString
import com.example.contentresolversampleapp.model.RandomStringRepository
import com.example.contentresolversampleapp.ui.theme.ContentResolverSampleAppTheme
import com.example.contentresolversampleapp.viewmodel.RandomStringViewModel
import com.example.contentresolversampleapp.viewmodel.RandomStringViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: RandomStringViewModel
    private var randomStringList: MutableList<RandomString> =arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val randomStringRepository= RandomStringRepository(this)
        viewModel = ViewModelProvider(this,RandomStringViewModelFactory(randomStringRepository))[RandomStringViewModel::class.java]
        viewModel.randomString.observe(this, Observer { randomString ->
            randomString?.let {
                randomStringList.add(0,randomString)
                Log.d("VALUE OF RANDOM STRING IS", it.toString())
            }
        })

        setContent {
            ContentResolverSampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreenView(viewModel,randomStringList)
                }
            }
        }
    }
}


@Composable
fun MainScreenView( viewModel: RandomStringViewModel,list: MutableList<RandomString>) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // TextField at the top
        val text = remember { androidx.compose.runtime.mutableStateOf("") }
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("Enter Text") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        // Button below the TextField
        Button(onClick = {
            viewModel.generateRandomString(text.value.toInt()) }) {
            Text("Click Me")
        }

        LazyColumn {
            items(list){
                item: RandomString ->
                RandomStringCard(randomString = item)
            }

        }

    }
}


@Composable
fun RandomStringCard(randomString: RandomString) {
    // Displaying each person in a card
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = "String: ${randomString.value}, Length: ${randomString.length}, TimeStamp: ${randomString.created}",
            modifier = Modifier.padding(16.dp)
        )
    }
}

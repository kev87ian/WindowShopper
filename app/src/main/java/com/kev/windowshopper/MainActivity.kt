package com.kev.windowshopper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kev.windowshopper.presentation.navigation.MainScreen
import com.kev.windowshopper.presentation.screen.amazon.AmazonViewModel
import com.kev.windowshopper.presentation.screen.jumia.JumiaViewModel
import com.kev.windowshopper.presentation.screen.walmart.WalmartViewModel
import com.kev.windowshopper.presentation.ui.theme.WindowShopperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WindowShopperTheme {
                /*       OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                value = query,
                onValueChange = {
                    query = it
                },
                placeholder = {
                    Text(text = "Search...")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
            )*/
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val jumiaViewModel = hiltViewModel<JumiaViewModel>()
                    val amazonViewModel = hiltViewModel<AmazonViewModel>()
                    val walmartViewModel = hiltViewModel<WalmartViewModel>()
                    var query by remember {
                        mutableStateOf("")
                    }
                    jumiaViewModel.searchProducts(query)
                    amazonViewModel.searchProduct(query)
                    Column {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            value = query,
                            onValueChange = {
                                query = it
                            },
                            placeholder = {
                                Text(text = "Search...")
                            },
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon"
                                )
                            }
                        )

                        MainScreen()
                    }
                }
            }
        }
    }
}

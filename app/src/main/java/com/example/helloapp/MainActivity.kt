package com.example.helloapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloapp.ui.theme.HelloAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ProductList(
                        products = listOf(
                            "Хлеб",
                            "Молоко",
                            "Яйца",
                            "Сыр",
                            "Яблоки",
                            "Картофель",
                            "Мясо",
                            "Масло"
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ProductList(products: List<String>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .padding(vertical = 6.dp)
            ) {
                Text(
                    text = product,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

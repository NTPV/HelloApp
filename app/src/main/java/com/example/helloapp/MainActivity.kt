@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.helloapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.helloapp.ui.theme.HelloAppTheme

data class ProductItem(val name: String, val bought: Boolean = false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDark by remember { mutableStateOf(false) }
            HelloAppTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductScreen(isDark = isDark, onToggleTheme = { isDark = !isDark })
                }
            }
        }
    }
}

@Composable
fun ProductScreen(isDark: Boolean, onToggleTheme: () -> Unit) {
    val products = remember {
        mutableStateListOf(
            ProductItem("Хлеб"),
            ProductItem("Молоко"),
            ProductItem("Яйца"),
            ProductItem("Сыр"),
            ProductItem("Яблоки"),
            ProductItem("Картофель"),
            ProductItem("Мясо"),
            ProductItem("Масло")
        )
    }
    var newProduct by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Список покупок") },
                actions = {
                    // Используем простой Text с эмодзи вместо проблемных иконок
                    TextButton(onClick = onToggleTheme) {
                        Text(if (isDark) "☀" else "🌙", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = newProduct,
                onValueChange = { newProduct = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Добавить продукт") },
                trailingIcon = {
                    IconButton(onClick = {
                        val trimmed = newProduct.trim()
                        if (trimmed.isNotEmpty()) {
                            products.add(ProductItem(trimmed))
                            newProduct = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить")
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProductList(products = products)
        }
    }
}

@Composable
fun ProductList(products: MutableList<ProductItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(products) { index, product ->
            ProductRow(
                item = product,
                onBoughtChange = { bought ->
                    products[index] = product.copy(bought = bought)
                },
                onDelete = {
                    products.removeAt(index)
                }
            )
        }
    }
}

@Composable
fun ProductRow(item: ProductItem, onBoughtChange: (Boolean) -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Checkbox(
                checked = item.bought,
                onCheckedChange = onBoughtChange
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}

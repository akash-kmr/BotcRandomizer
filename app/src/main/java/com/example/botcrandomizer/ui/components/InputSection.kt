package com.example.botcrandomizer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputSection(
    label: String,
    items: MutableList<String>,
    newItem: String,
    onNewItemChange: (String) -> Unit,
    onAddItem: () -> Unit,
    errorMessage: String? = null // Added error message to handle duplicates
) {
    Text(label, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

    Column {
        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(item, Modifier.weight(1f), fontSize = 16.sp)
                IconButton(onClick = { items.removeAt(index) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Item", tint = Color.Red)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newItem,
                onValueChange = onNewItemChange,
                placeholder = { Text("Enter new $label") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default,
                isError = errorMessage != null // Display error if duplicate
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onAddItem) {
                Text("Add")
            }
        }

        // Display error message if duplicate
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

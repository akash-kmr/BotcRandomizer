package com.example.botcrandomizer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownWithChips(
    label: String,
    availableItems: List<String>,
    selectedItems: List<String>,
    onAddItem: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(label, style = MaterialTheme.typography.titleMedium)

        // Display selected items as chips
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedItems.size) { index ->
                Chip(
                    label = selectedItems[index],
                    onRemove = { onRemoveItem(selectedItems[index]) }
                )
            }
        }

        // Use OutlinedTextField instead of TextFieldDecorationBox for the input
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    expanded = it.text.isNotEmpty()
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter or select $label") },
                singleLine = true
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                val filteredItems = availableItems.filter { it.contains(textFieldValue.text, true) }
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onAddItem(item)
                            textFieldValue = TextFieldValue("") // Reset TextField
                            expanded = false
                        },
                        text = { Text(item) }
                    )
                }
            }
        }

        // Button to add custom typed item if not from the list
        Spacer(modifier = Modifier.height(8.dp))
        if (textFieldValue.text.isNotBlank() && !selectedItems.contains(textFieldValue.text)) {
            Button(
                onClick = {
                    onAddItem(textFieldValue.text)
                    textFieldValue = TextFieldValue("")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add $label")
            }
        }
    }
}

@Composable
fun Chip(label: String, onRemove: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(16.dp),  // More rounded corners
        modifier = Modifier.padding(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp), // Smaller padding
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = 12.sp, color = Color.White) // Smaller text size
            IconButton(onClick = onRemove, modifier = Modifier.size(16.dp)) { // Smaller icon size
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove $label",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp) // Adjust icon size
                )
            }
        }
    }
}

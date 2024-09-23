package com.example.botcrandomizer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericSelectionDialog(
    availableItems: List<String>,
    selectedItems: List<String>,  // Prepopulate the dialog with selected items
    onDismiss: () -> Unit,
    onSelect: (List<String>) -> Unit
) {
    var currentSelection = remember { mutableStateListOf<String>().apply { addAll(selectedItems) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Items") },
        text = {
            LazyColumn {
                items(availableItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                if (currentSelection.contains(item)) {
                                    currentSelection.remove(item)
                                } else {
                                    currentSelection.add(item)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = currentSelection.contains(item),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    if (!currentSelection.contains(item)) {
                                        currentSelection.add(item)
                                    }
                                } else {
                                    currentSelection.remove(item)
                                }
                            }
                        )
                        Text(item, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSelect(currentSelection.toList())  // Return selected items
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

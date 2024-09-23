package com.example.botcrandomizer.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.botcrandomizer.ui.components.Chip

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectionSection(
    label: String,
    selectedItems: MutableList<String>,
    newItem: String,
    onNewItemChange: (String) -> Unit,
    onAddItem: () -> Unit,
    availableItems: List<String>
) {
    var showBrowseDialog by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .padding(8.dp)
    ) {
        // The main box with the rounded boundary
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,  // Boundary thickness
                    color = MaterialTheme.colorScheme.primary, // Boundary color
                    shape = RoundedCornerShape(16.dp) // Rounded boundary
                )
                .padding(16.dp) // Padding inside the border
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedItems.forEach { item ->
                        Chip(
                            label = item,
                            onRemove = { selectedItems.remove(item) }
                        )
                    }
                }

                OutlinedTextField(
                    value = newItem,
                    onValueChange = { input ->
                        if (input.endsWith('\n')) { // Check if the last character is a newline
                            onAddItem() // Add the item
                            onNewItemChange("") // Clear the input
                        } else {
                            onNewItemChange(input) // Update the text input normally
                        }
                    },
                    placeholder = { Text("Type $label name") },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Button(
                    onClick = onAddItem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Add $label")
                }

                Button(
                    onClick = { showBrowseDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Browse $label")
                }

                if (showBrowseDialog) {
                    GenericSelectionDialog(
                        availableItems = availableItems,
                        selectedItems = selectedItems,
                        onDismiss = { showBrowseDialog = false },
                        onSelect = { selected ->
                            selectedItems.clear()
                            selectedItems.addAll(selected)
                            showBrowseDialog = false
                        }
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.TopStart) // Aligns the banner to the top left of the boundary
                .offset(y = (-24).dp) // Adjusted to move the banner further up
                .clip(CutCornerShape(bottomEnd = 24.dp)) // Flag or banner shape effect with a cut corner
                .wrapContentWidth() // Dynamically adjust width based on content
                .padding(horizontal = 8.dp), // Optional: add horizontal padding to create space on sides
            color = MaterialTheme.colorScheme.primary // Primary color for the banner
        ) {
            Text(
                text = label.uppercase(), // Capitalizes the text
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), // Increased vertical padding
                fontSize = 20.sp, // Larger font size
                color = MaterialTheme.colorScheme.onPrimary // Text color on the banner
            )
        }


    }
}

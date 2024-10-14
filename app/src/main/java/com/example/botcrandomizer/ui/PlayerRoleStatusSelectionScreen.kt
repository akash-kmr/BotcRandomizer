package com.example.botcrandomizer.ui

import com.example.botcrandomizer.ui.components.AssignmentResultTable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.botcrandomizer.data.PlayerData
import com.example.botcrandomizer.logic.assignRolesAndStatuses
import com.example.botcrandomizer.model.AssignmentResult
import com.example.botcrandomizer.utils.trimAndCapitalize

@Composable
fun PlayerRoleStatusSelectionScreen(
    onAssignRoles: (List<AssignmentResult>) -> Unit // Callback for navigation
) {
    // Using simple mutable lists instead of SnapshotStateList
    val players = rememberSaveable(        key = "players",
        saver = stringListSaver) {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.players.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    val roles = rememberSaveable(        key = "roles",
        saver = stringListSaver) {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.roles.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    val statuses = rememberSaveable(        key = "statuses",
        saver = stringListSaver) {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.statuses.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    val evilRoles = rememberSaveable(key = "statuses", saver = stringListSaver) {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.evilRoles.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    // State for input text fields
    var newPlayer by rememberSaveable { mutableStateOf("") }
    var newRole by rememberSaveable { mutableStateOf("") }
    var newStatus by rememberSaveable { mutableStateOf("") }
    var newEvilRole by rememberSaveable { mutableStateOf("") }

// State for assignment results as strings
    var assignmentResults: List<AssignmentResult> = rememberSaveable(saver = assignmentResultSaver) {
        mutableStateListOf<AssignmentResult>()  // Initialize with an empty mutable state list
    }

    // Scroll state for vertical scrolling
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(64.dp)) // Space for the header

            // Players Section
            SelectionSection(
                label = "Players",
                selectedItems = players,
                newItem = newPlayer,
                onNewItemChange = { newPlayer = it },
                onAddItem = {
                    val processedNewPlayer = newPlayer.trimAndCapitalize() // Apply transformation here
                    if (processedNewPlayer.isNotBlank() && !players.contains(processedNewPlayer)) {
                        players.add(processedNewPlayer)
                        newPlayer = ""
                    }
                },
                availableItems = PlayerData.players.map { it.name.trimAndCapitalize() } // Example list for browse
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Roles Section
            SelectionSection(
                label = "Roles",
                selectedItems = roles,
                newItem = newRole,
                onNewItemChange = { newRole = it },
                onAddItem = {
                    val processedNewRole = newRole.trimAndCapitalize()
                    if (processedNewRole.isNotBlank() && !roles.contains(processedNewRole)) {
                        roles.add(processedNewRole)
                        newRole = ""
                    }
                },
                availableItems = PlayerData.roles.map { it.name.trimAndCapitalize() } // Example roles
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Statuses Section
            SelectionSection(
                label = "Statuses",
                selectedItems = statuses,
                newItem = newStatus,
                onNewItemChange = { newStatus = it },
                onAddItem = {
                    val processedNewStatus = newStatus.trimAndCapitalize() // Apply transformation
                    if (processedNewStatus.isNotBlank() && !statuses.contains(processedNewStatus)) {
                        statuses.add(processedNewStatus)
                        newStatus = ""
                    }
                },
                availableItems = PlayerData.statuses.map { it.name.trimAndCapitalize() } // Example statuses
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Evil Roles Section
            SelectionSection(
                label = "Evil Roles",
                selectedItems = evilRoles,
                newItem = newEvilRole,
                onNewItemChange = { newEvilRole = it },
                onAddItem = {
                    val processedNewEvilRole = newEvilRole.trimAndCapitalize() // Apply transformation
                    if (processedNewEvilRole.isNotBlank() && !evilRoles.contains(processedNewEvilRole)) {
                        evilRoles.add(processedNewEvilRole)
                        newEvilRole = ""
                    }
                },
                availableItems = PlayerData.evilRoles.map { it.name.trimAndCapitalize() } // Example evil roles
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Assign Roles Button
            Button(
                onClick = {
                    assignmentResults = assignRolesAndStatuses(
                        players = players,
                        roles = roles,
                        statuses = statuses,
                        evilRoles = evilRoles,
                        assignStatusToEvil = false // or false based on your logic
                    )
                    // Trigger navigation to AssignmentResultScreen with results
                    onAssignRoles(assignmentResults)
                },
                modifier = Modifier.padding(vertical = 8.dp),
                enabled = players.isNotEmpty() && roles.isNotEmpty() &&
                        statuses.size <= players.size && roles.size >= players.size &&
                        evilRoles.size < players.size
            ) {
                Text("Assign Roles")
            }

            // Display the assignment results
            if (assignmentResults.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                AssignmentResultTable(results = assignmentResults)
            }
        }

        // Header at the top
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.primary
        ) {
            Text(
                text = "BOTC Randomizer",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}



// Create a custom Saver for SnapshotStateList<String> to convert it to a String
val stringListSaver = Saver<SnapshotStateList<String>, String>(
    save = { it.joinToString(",") },  // Convert List to comma-separated String
    restore = { it.split(",").toSnapshotStateList() }  // Convert String back to SnapshotStateList
)

// Extension function to convert List<String> to SnapshotStateList<String>
fun List<String>.toSnapshotStateList(): SnapshotStateList<String> {
    return mutableStateListOf<String>().apply { addAll(this@toSnapshotStateList) }
}

val assignmentResultSaver = listSaver<MutableList<AssignmentResult>, String>(
    save = { list -> list.map { it.toCsvString() } },
    restore = { list -> list.map { AssignmentResult.fromCsvString(it) }.toMutableList() }
)
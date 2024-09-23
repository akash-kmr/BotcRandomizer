package com.example.botcrandomizer.ui

import AssignmentResultTable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import com.example.botcrandomizer.data.PlayerData
import com.example.botcrandomizer.logic.assignRolesAndStatuses
import com.example.botcrandomizer.model.AssignmentResult

@Composable
fun PlayerRoleStatusSelectionScreen() {
    // State for players, roles, statuses, and evil roles
    val players = remember { mutableStateListOf<String>().apply { addAll(PlayerData.commonPlayers.map { it.trimAndCapitalize() }) } }
    val roles = remember { mutableStateListOf<String>().apply { addAll(PlayerData.commonRoles.map { it.trimAndCapitalize() }) } }
    val statuses = remember { mutableStateListOf<String>().apply { addAll(PlayerData.commonStatuses.map { it.trimAndCapitalize() }) } }
    val evilRoles = remember { mutableStateListOf<String>().apply { addAll(PlayerData.commonEvilRoles.map { it.trimAndCapitalize() }) } }

    // Transform PlayerData lists
    val availablePlayers = PlayerData.players.map { it.trimAndCapitalize() }
    val availableRoles = PlayerData.roles.map { it.trimAndCapitalize() }
    val availableStatuses = PlayerData.statuses.map { it.trimAndCapitalize() }
    val availableEvilRoles = PlayerData.evilRoles.map { it.trimAndCapitalize() }

    // State for input text fields
    var newPlayer by remember { mutableStateOf("") }
    var newRole by remember { mutableStateOf("") }
    var newStatus by remember { mutableStateOf("") }
    var newEvilRole by remember { mutableStateOf("") }

    // State for assignment results
    var assignmentResults by remember { mutableStateOf<List<AssignmentResult>>(emptyList()) }

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
                availableItems = availablePlayers // Example list for browse
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
                availableItems = availableRoles // Example roles
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
                availableItems = availableStatuses // Example statuses
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Evil Roles Section
            SelectionSection(
                label = "Evil Roles",
                selectedItems = evilRoles,
                newItem = newEvilRole,
                onNewItemChange = { newEvilRole = it},
                onAddItem = {
                    val processedNewEvilRole = newEvilRole.trimAndCapitalize() // Apply transformation
                    if (processedNewEvilRole.isNotBlank() && !evilRoles.contains(processedNewEvilRole)) {
                        evilRoles.add(processedNewEvilRole)
                        newEvilRole = ""
                    }
                },
                availableItems = availableEvilRoles // Example evil roles
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

// Helper function to trim and capitalize the string
fun String.trimAndCapitalize(): String {
    return this.trim().uppercase() // Trimming and converting to uppercase
}

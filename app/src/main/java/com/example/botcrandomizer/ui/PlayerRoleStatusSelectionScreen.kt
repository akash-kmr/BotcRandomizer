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
import com.example.botcrandomizer.data.PlayerData
import com.example.botcrandomizer.logic.assignRolesAndStatuses
import com.example.botcrandomizer.model.AssignmentResult
import com.example.botcrandomizer.utils.trimAndCapitalize

@Composable
fun PlayerRoleStatusSelectionScreen() {

    // Mutable list for common players, roles, etc., extracting names from Entity
    val players = remember {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.players.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    val roles = remember {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.roles.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    val statuses = remember {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.statuses.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    val evilRoles = remember {
        mutableStateListOf<String>().apply {
            addAll(PlayerData.evilRoles.filter { it.isCommon }.map { it.name.trimAndCapitalize() })
        }
    }

    // Transform PlayerData lists for all available data (without the isCommon filter)
    val availablePlayers = PlayerData.players.map { it.name.trimAndCapitalize() }
    val availableRoles = PlayerData.roles.map { it.name.trimAndCapitalize() }
    val availableStatuses = PlayerData.statuses.map { it.name.trimAndCapitalize() }
    val availableEvilRoles = PlayerData.evilRoles.map { it.name.trimAndCapitalize() }

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

package com.example.botcrandomizer.logic

import com.example.botcrandomizer.model.AssignmentResult

fun assignRolesAndStatuses(
    players: List<String>,
    roles: List<String>,
    statuses: List<String>,
    evilRoles: List<String>,
    assignStatusToEvil: Boolean
): List<AssignmentResult> {
    if (players.isEmpty() || roles.isEmpty() || evilRoles.isEmpty()) return emptyList()

    val resultList = mutableListOf<AssignmentResult>()
    val shuffledRoles = roles.shuffled()
    val shuffledEvilRoles = evilRoles.shuffled().toMutableList()

    // Randomly select players who will be evil
    val evilPlayerIndices = players.indices.shuffled().take(shuffledEvilRoles.size)

    // Prepare a list for assigning statuses
    val shuffledStatuses = statuses.shuffled().toMutableList()

    // Separate players into evil and good groups
    val goodPlayerIndices = players.indices.filter { !evilPlayerIndices.contains(it) }
    val eligibleStatusPlayers = if (assignStatusToEvil) players.indices.toList() else goodPlayerIndices

    if (shuffledStatuses.size > eligibleStatusPlayers.size) {
        throw IllegalArgumentException("Not enough eligible players to assign all statuses uniquely.")
    }

    val statusAssignments = eligibleStatusPlayers.shuffled().zip(shuffledStatuses)

    players.forEachIndexed { index, player ->
        val role = shuffledRoles.getOrNull(index % shuffledRoles.size) ?: "No role"
        val isEvil = evilPlayerIndices.contains(index)
        val evilRole = if (isEvil && shuffledEvilRoles.isNotEmpty()) shuffledEvilRoles.removeFirst() else null
        val status = statusAssignments.firstOrNull { it.first == index }?.second ?: "None"

        resultList.add(AssignmentResult(player, role, status, isEvil, evilRole))
    }

    return resultList
}

package com.example.botcrandomizer.model

import androidx.compose.runtime.saveable.Saver

//data class AssignmentResult1(
//    val player: String,
//    val role: String,
//    val status: String,
//    val isEvil: Boolean,
//    val evilRole: String?,
//    val isAlive: Boolean = false
//)

// Example data class
data class AssignmentResult(
    val player: String,
    val role: String,
    val status: String,
    val isEvil: Boolean,
    val evilRole: String?,
    val isAlive: Boolean = true
) {
    fun toCsvString(): String {
        return "$player,$role,${status ?: "None"},$isEvil,${evilRole ?: "None"},$isAlive"
    }

    companion object {
        fun fromCsvString(csv: String): AssignmentResult {
            val parts = csv.split(",")
            return AssignmentResult(
                player = parts[0],
                role = parts[1],
                status = (if (parts[2] == "None") null else parts[2]).toString(),
                isEvil = parts[3].toBoolean(),
                evilRole = if (parts[4] == "None") null else parts[4],
                isAlive = parts[5].toBoolean()
            )
        }
    }
}

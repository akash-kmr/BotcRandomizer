package com.example.botcrandomizer.model

data class AssignmentResult(
    val player: String,
    val role: String,
    val status: String,
    val isEvil: Boolean,
    val evilRole: String?
)

package com.example.botcrandomizer.data

// Define a data class for storing name and common flag
data class Entity(val name: String, val isCommon: Boolean)

object PlayerData {
        val players = listOf(
                Entity("Akash", true),
                Entity("Anzum", true),
                Entity("Mansi", true),
                Entity("Megha", true),
                Entity("Ankita", true),
                Entity("Rajat", true),
                Entity("Aadhitya", true),
                Entity("Shailendra", true),
                Entity("Abhishek", false),
                Entity("Ishaan", false),
                Entity("Poonam", false),
                Entity("Chirag", true),
                Entity("Aakanksha", true),
                Entity("Ilias", true),
                Entity("Arpit", false)
        )
        val roles = listOf(
                Entity("Empath", true),
                Entity("Slayer", true),
                Entity("Monk", true),
                Entity("Undertaker", true),
                Entity("Sage", false),
                Entity("Gambler", true),
                Entity("Artist", true),
                Entity("Philosopher", false),
                Entity("Snakecharmer", false),
                Entity("Saint", false),
                Entity("Fool", false),
                Entity("Soldier", false),
                Entity("Grandmother", false),
                Entity("Klutz", false),
                Entity("Seamstress", false),
                Entity("Chef", false)
        )

        val statuses = listOf(
                Entity("Drunk", true)
        )

        val evilRoles = listOf(
                Entity("Demon", true),
                Entity("Poisoner", true),
                Entity("Witch", true),
                Entity("Chhota Evil", false),
                Entity("Word evil", false)
        )
}

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
                Entity("Sage", true),
                Entity("Gambler", true),
                Entity("Artist", true),
                Entity("Philosopher", true),
                Entity("Snakecharmer", true),
                Entity("Saint", true),
                Entity("Fool", true),
                Entity("Soldier", true),
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
                Entity("Witch", false),
                Entity("Chhota Evil", true),
                Entity("Word evil", false)
        )
}

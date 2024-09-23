package com.example.botcrandomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.botcrandomizer.ui.PlayerRoleStatusSelectionScreen
import com.example.botcrandomizer.ui.theme.BotcRandomizerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BotcRandomizerTheme {
                PlayerRoleStatusSelectionScreen()
            }
        }
    }
}
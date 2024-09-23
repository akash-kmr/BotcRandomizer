import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.botcrandomizer.model.AssignmentResult

@Composable
fun AssignmentResultTable(results: List<AssignmentResult>) {
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    Column(modifier = Modifier.fillMaxWidth()) {
        // Copy Button at the top
        Button(
            onClick = {
                val (goodPlayers, evilPlayers) = results.partition { !it.isEvil }

                val copyText = buildString {
                    append("Good Players:\n")
                    goodPlayers.forEach { result ->
                        val statusText = if (result.status != "None") ", Status: ${result.status}" else ""
                        append("${result.player} - Role: ${result.role}$statusText\n")
                    }
                    append("\nEvil Players:\n")
                    evilPlayers.forEach { result ->
                        append("${result.player} - Role: ${result.evilRole}, Pretend: ${result.role}\n")
                    }
                }

                val clipData = ClipData.newPlainText("Assignment Results", copyText)
                clipboardManager.setPrimaryClip(clipData)
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Copy All Data")
        }

        // Results cards
        results.forEach { result ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = if (result.isEvil) Color(0xFFB22222) else Color(0xFF4CAF50)) // Dark red and green
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = result.player, color = Color.White)

                        if (result.isEvil) {
                            Text(text = "Role: ${result.evilRole}", color = Color.White)
                        } else {
                            Text(text = "Role: ${result.role}", color = Color.White)
                        }

                        // Show Status only if it's not "None"
                        if (result.status != "None") {
                            Text(text = "Status: ${result.status}", color = Color.White)
                        }
                    }
                    if (result.isEvil) {
                        Text(
                            text = "Pretend: ${result.role}",
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

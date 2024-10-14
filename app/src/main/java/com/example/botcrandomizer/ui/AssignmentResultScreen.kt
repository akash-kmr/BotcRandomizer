import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.botcrandomizer.model.AssignmentResult
import com.example.botcrandomizer.ui.components.AssignmentResultTable

@Composable
fun AssignmentResultScreen(
    results: List<AssignmentResult>,
    onBack: () -> Unit // Callback for the back button
) {
    // Scroll state for vertical scrolling
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState) // Enable scrolling
    ) {
        // Back Button
        Button(
            onClick = onBack,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Back")
        }

        // Assignment Results Table
        AssignmentResultTable(results = results)
    }
}

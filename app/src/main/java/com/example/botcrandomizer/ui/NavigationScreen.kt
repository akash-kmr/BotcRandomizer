import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.botcrandomizer.model.AssignmentResult
import com.example.botcrandomizer.ui.PlayerRoleStatusSelectionScreen

@Composable
fun NavigationScreen() {
    // NavController remembers its state across configuration changes
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "selection_screen") {
        composable("selection_screen") {
            PlayerRoleStatusSelectionScreen { results ->
                // Save results in SavedStateHandle to persist them
                navController.currentBackStackEntry?.savedStateHandle?.set("assignmentResults", results)
                navController.navigate("result_screen")
            }
        }
        composable("result_screen") {
            // Retrieve results from SavedStateHandle to ensure persistence
            val results = navController.previousBackStackEntry?.savedStateHandle?.get<List<AssignmentResult>>("assignmentResults")
            if (results != null) {
                AssignmentResultScreen(results = results, onBack = {
                    navController.popBackStack()
                })
            } else {
                Text("No results found") // Handle case where no results are passed
            }
        }
    }
}

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botcrandomizer.data.local.PlayerEntity
import com.example.botcrandomizer.data.repository.PlayerRepository
import com.example.botcrandomizer.utils.trimAndCapitalize
import kotlinx.coroutines.launch

class PlayerViewModel(private val repository: PlayerRepository) : ViewModel() {

    val players = repository.getAllPlayers()

    fun addPlayer(name: String, isCommon: Boolean) {
        viewModelScope.launch {
            // Trim and capitalize the name before inserting
            val player = PlayerEntity(name = name.trimAndCapitalize(), isCommon = isCommon)
            repository.insertPlayer(player)
        }
    }

    fun updatePlayer(player: PlayerEntity) {
        viewModelScope.launch {
            // Trim and capitalize the player's name before updating
            val updatedPlayer = player.copy(name = player.name.trimAndCapitalize())
            repository.updatePlayer(updatedPlayer)
        }
    }

    fun deletePlayer(player: PlayerEntity) {
        viewModelScope.launch {
            // Trim and capitalize the player's name before deleting
            val playerToDelete = player.copy(name = player.name.trimAndCapitalize())
            repository.deletePlayer(playerToDelete)
        }
    }
}

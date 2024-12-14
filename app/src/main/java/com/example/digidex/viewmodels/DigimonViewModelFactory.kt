import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digidex.repositories.DigimonRepository
import com.example.digidex.viewmodels.DigimonViewModel

class DigimonViewModelFactory(private val repository: DigimonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DigimonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DigimonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

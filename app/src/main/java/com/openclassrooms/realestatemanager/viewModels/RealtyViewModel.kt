
package com.openclassrooms.realestatemanager.viewModels
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.repository.RealtyRepository
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class RealtyViewModel(private val repository: RealtyRepository) : ViewModel() {

    val allRealty: LiveData<List<Realty>> = repository.getAllRealtyNewerToOlder.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(realty: Realty) = viewModelScope.launch {
        repository.insert(realty)
    }
}

//Erreur Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option lié à la version de appcompat
//OK pour la version '1.4.1'
//CA MARCHE PAS Avec version '1.5.1 '
//
class RealtyViewModelFactory(private val repository: RealtyRepository) : ViewModelProvider .Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RealtyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RealtyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

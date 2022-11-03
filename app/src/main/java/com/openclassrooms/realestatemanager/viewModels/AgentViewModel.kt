package com.openclassrooms.realestatemanager.viewModels

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Agent
import com.openclassrooms.realestatemanager.repository.AgentRepository
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class AgentViewModel(private val repository: AgentRepository) : ViewModel() {

    val allAgent: LiveData<List<Agent>> = repository.getAllAgent.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(agent: Agent) = viewModelScope.launch {
        repository.insert(agent)
    }
}

//Erreur Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option lié à la version de appcompat
//OK pour la version '1.4.1'
//CA MARCHE PAS Avec version '1.5.1 '
//
class AgentViewModelFactory(private val repository: AgentRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

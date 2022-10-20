
package com.openclassrooms.realestatemanager.viewModels
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Realty
import com.openclassrooms.realestatemanager.models.RealtyItem
import com.openclassrooms.realestatemanager.models.Shot
import com.openclassrooms.realestatemanager.repository.RealtyRepository
import com.openclassrooms.realestatemanager.repository.ShotRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.security.acl.Owner
import java.util.concurrent.Flow

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class RealtyViewModel(private val repository: RealtyRepository, val shotRepository: ShotRepository) : ViewModel() {

    val allRealty: LiveData<List<Realty>> = repository.getAllRealtyNewerToOlder.asLiveData()
    val allShoot: LiveData<List<Shot>> = shotRepository.getAllShot.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun getRealtyById(id: Int): LiveData<Realty> {
        return repository.getRealtyById(id).asLiveData()
    }
    fun insert(realty: Realty):MutableLiveData<Long>{
        var id = MutableLiveData<Long>()
        viewModelScope.launch {
            id.postValue(repository.insert(realty))
        }
        return id
    }
    fun delete(realty: Realty) = viewModelScope.launch {
        realty.idRealty?.let {
            repository.delete(it) }
    }
    fun updateRealty(realty: Realty) = viewModelScope.launch {
            repository.updateRealty(realty)
    }
    fun updateStatusRealty(id:Int) = viewModelScope.launch {
        repository.updateStatusRealty(id)   }


         fun allShotByIdRealty(id: Int): LiveData<List<Shot>> {
            return shotRepository.getAllShotByIdRealty(id).asLiveData()
        }

    fun insert(shot: Shot):MutableLiveData<Long>{
        var id = MutableLiveData<Long>()
        viewModelScope.launch {
            id.postValue(shotRepository.insert(shot))
        }
        return id
    }
    val getAllRealtyItem:LiveData<List<RealtyItem>> = repository.getAllRealtyItem.asLiveData()


    fun deleteByIdRealty(idRealty: Int) =viewModelScope.launch {
        idRealty?.let {
            repository.delete(it)
        }
    }
}

//Erreur Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option lié à la version de appcompat
//OK pour la version '1.4.1'
//CA MARCHE PAS Avec version '1.5.1 '
//
class RealtyViewModelFactory(private val repository: RealtyRepository,private val shotRepository: ShotRepository) : ViewModelProvider .Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RealtyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RealtyViewModel(repository,shotRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

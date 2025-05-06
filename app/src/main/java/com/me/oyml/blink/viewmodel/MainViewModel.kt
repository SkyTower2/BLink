package com.me.oyml.blink.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.oyml.blink.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _permissionsGranted = MutableLiveData<Boolean>()
    val permissionsGranted: LiveData<Boolean> = _permissionsGranted

    private val _navCommand = MutableLiveData<NavCommand>()
    val navCommand: LiveData<NavCommand> = _navCommand

    fun onPermissionsResult(result: Map<String, Boolean>) {
        val allGranted = result.all { it.value }
        _permissionsGranted.value = allGranted
    }

    fun onDrawerItemClicked(itemId: Long) {
        when (itemId) {
            1L -> _navCommand.postValue(NavCommand.ShowSearchList)
            2L -> _navCommand.postValue(NavCommand.ShowSearchSettings)
            3L -> _navCommand.postValue(NavCommand.ShowCollect)
            4L -> _navCommand.postValue(NavCommand.ShowPermission)
            5L -> _navCommand.postValue(NavCommand.ShowAbout)
            6L -> _navCommand.postValue(NavCommand.ShowOta)
            7L -> _navCommand.postValue(NavCommand.ShowGuide)
            else -> Unit
        }
    }

    fun onSwitchChanged(identifier: Long, isChecked: Boolean) {
        if (identifier == 8L) {
            mainRepository.toggleSearch(isChecked)
        }
    }

    sealed class NavCommand {
        data object ShowSearchList : NavCommand()
        data object ShowSearchSettings : NavCommand()
        data object ShowCollect : NavCommand()
        data object ShowPermission : NavCommand()
        data object ShowAbout : NavCommand()
        data object ShowGuide : NavCommand()
        data object ShowOta : NavCommand()
    }
}

package com.me.oyml.blink.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


fun provideMainViewModelFactory(
): MainViewModelFactory {
    return MainViewModelFactory(MainRepository())
}

class MainViewModelFactory @Inject constructor(
    private val repository: MainRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}

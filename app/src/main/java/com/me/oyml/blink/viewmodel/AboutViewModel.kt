package com.me.oyml.blink.viewmodel

import androidx.lifecycle.ViewModel
import com.me.oyml.blink.repository.AboutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val aboutRepository: AboutRepository
) : ViewModel() {
}
package com.me.oyml.blink.viewmodel

import com.me.oyml.blink.repository.SearchSettingsRepository
import com.me.oyml.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchSettingsViewModel @Inject constructor(
    private val repository: SearchSettingsRepository
) : BaseViewModel() {
}
package com.me.oyml.blink.ui.fragment

import com.me.oyml.blink.databinding.SearchSettingsActivityBinding
import com.me.oyml.blink.viewmodel.SearchSettingsViewModel
import com.me.oyml.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSettingsFragment :
    BaseFragment<SearchSettingsActivityBinding, SearchSettingsViewModel>(
        share = false,
        factory = null
    ) {

    override fun SearchSettingsActivityBinding.initBinding() {
        // 可以继续绑定点击事件、设置 Adapter 等
    }
}
package com.me.oyml.blink.ui.fragment

import com.me.oyml.blink.databinding.AboutFragmentBinding
import com.me.oyml.blink.viewmodel.AboutViewModel
import com.me.oyml.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<AboutFragmentBinding, AboutViewModel>(
    share = false,
    factory = null
) {
    override fun AboutFragmentBinding.initBinding() {
    }
}
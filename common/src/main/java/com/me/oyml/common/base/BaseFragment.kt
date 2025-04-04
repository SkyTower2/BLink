package com.me.oyml.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.me.oyml.common.extensions.getViewBinding
import com.me.oyml.common.utils.ViewModelUtils

abstract class BaseFragment<VB : ViewDataBinding, VM : ViewModel>(
    private val share: Boolean = false,
    private val factory: ViewModelProvider.Factory? = null
) : Fragment(), BaseBinding<VB> {
    protected lateinit var mBinding: VB
        private set
    protected lateinit var viewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = getViewBinding(inflater, container)
        viewModel = if (share) ViewModelUtils.createActivityViewModel(this, factory, 1)
            else ViewModelUtils.createViewModel(this, factory, 1)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.initBinding()
    }


    open fun onBackPressed(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }
}
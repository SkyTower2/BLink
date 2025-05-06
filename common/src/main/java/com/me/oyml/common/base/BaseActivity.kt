package com.me.oyml.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.me.oyml.common.extensions.getViewBinding
import com.me.oyml.common.utils.ViewModelUtils

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel>(
    private val factory: ViewModelProvider.Factory?
) : AppCompatActivity(), BaseBinding<VB> {
    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        viewModel = ViewModelUtils.createViewModel(this, factory, 1)
        initData(savedInstanceState) // 新增初始化数据方法
        mBinding.initBinding()
        initObserve()
    }

    /**
     * 初始化数据，子类可重写此方法使用savedInstanceState
     */
    open fun initData(savedInstanceState: Bundle?) {
        // 默认空实现
    }

    abstract fun initObserve()

    override fun onBackPressed() {
        super.onBackPressed()
        val fragments: List<Fragment> = supportFragmentManager.fragments
        if (!fragments.isNullOrEmpty()) {
            for (fragment in fragments) {
                if (fragment is BaseFragment<*, *>) {
                    if (fragment.onBackPressed()) {
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }
}
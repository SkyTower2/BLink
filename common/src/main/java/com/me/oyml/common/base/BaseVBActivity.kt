package com.me.oyml.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.me.oyml.common.extensions.getViewBinding

abstract class BaseVBActivity<VB : ViewDataBinding> : AppCompatActivity(), BaseBinding<VB> {

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
       getViewBinding(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContentView(mBinding.root)
        initData(savedInstanceState) // 新增初始化数据方法
        initObserve()
        mBinding.initBinding()
    }

    abstract fun initView()

    /**
     * 初始化数据，子类可重写此方法使用savedInstanceState
     */
    open fun initData(savedInstanceState: Bundle?) {
        // 默认空实现
    }

    abstract fun initObserve()

}
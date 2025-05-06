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

/**
 * @param VB 布局资源 ID
 * @param VM ViewModel 的 KClass
 * @param share 是否与 Activity 共享同一个 ViewModel
 * @param factory 自定义的 ViewModelProvider.Factory，可选
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : ViewModel>(
    private val share: Boolean,
    private val factory: ViewModelProvider.Factory?
) : Fragment(), BaseBinding<VB> {
    protected lateinit var mBinding: VB
        private set
    protected lateinit var viewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 获取布局绑定类，getViewBinding() 是自定义扩展函数
        mBinding = getViewBinding(inflater, container)

        /**
         * 通过 ViewModelUtils 创建 ViewModel
         * share == true：通过 requireActivity() 获取 Activity 范围的 ViewModel。
         * 否则用当前 Fragment 的 ViewModelStore 获取
         */
        viewModel = if (share) ViewModelUtils.createActivityViewModel(this, factory, 1)
        else ViewModelUtils.createViewModel(this, factory, 1)
        return mBinding.root
    }

    /**
     * 在 Fragment 的视图被创建后调用
     * 继承 BaseFragment 的子类中，必须去实现这个 VB.initBinding() 扩展函数
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.initBinding()
    }


    /**
     * 处理返回键事件
     * 默认返回 false，可以被子类重写，实现 Fragment 拦截返回键。
     */
    open fun onBackPressed(): Boolean {
        return false
    }

    /**
     * 释放 DataBinding，防止内存泄漏。
     */
    override fun onDestroy() {
        super.onDestroy()
        if (::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }
}
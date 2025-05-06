package com.me.oyml.common.base

import androidx.databinding.ViewDataBinding

/**
 * BaseBinding 接口
 * VB: ViewDataBinding：泛型参数 VB 必须继承自 ViewDataBinding。
 * fun VB.initBinding()：这是一个 扩展函数声明，意味着这个函数是扩展在 VB 类型（也就是 ViewBinding 类）上的。
 */
interface BaseBinding<VB : ViewDataBinding> {
    fun VB.initBinding()
}
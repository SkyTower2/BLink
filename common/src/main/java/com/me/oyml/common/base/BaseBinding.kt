package com.me.oyml.common.base

import androidx.databinding.ViewDataBinding

interface BaseBinding <VB: ViewDataBinding> {
    fun VB.initBinding()
}
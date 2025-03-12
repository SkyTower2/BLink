package com.me.oyml.blink.app

import android.util.Log
import com.me.oyml.blink.databinding.MainActivityBinding
import com.me.oyml.common.base.BaseActivity
import com.me.oyml.common.utils.KLog

class MainActivity: BaseActivity<MainActivityBinding>() {
    override fun MainActivityBinding.initBinding() {
        // init binding
        KLog.d("btnClick")
    }
}
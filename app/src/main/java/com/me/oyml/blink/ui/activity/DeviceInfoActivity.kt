package com.me.oyml.blink.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.me.oyml.blink.constants.ARouterPath
import com.me.oyml.blink.databinding.DeviceinfoActivityBinding
import com.me.oyml.blink.viewmodel.DeviceInfoViewModel
import com.me.oyml.common.base.BaseActivity

@Route(path = ARouterPath.DEVICE_DETAIL)
class DeviceInfoActivity :
    BaseActivity<DeviceinfoActivityBinding, DeviceInfoViewModel>(factory = null) {
    override fun initObserve() {
    }

    override fun DeviceinfoActivityBinding.initBinding() {
    }
}
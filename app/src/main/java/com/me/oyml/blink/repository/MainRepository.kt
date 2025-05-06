package com.me.oyml.blink.repository

import com.me.oyml.common.base.BaseRepository
import com.me.oyml.common.utils.KLog
import javax.inject.Inject

class MainRepository @Inject constructor():BaseRepository() {
    fun toggleSearch(enable: Boolean) {
        // 开启或关闭连续搜索逻辑
        KLog.e("toggleSearch", "enable: $enable")
    }
}
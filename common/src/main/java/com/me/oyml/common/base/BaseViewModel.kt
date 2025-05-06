package com.me.oyml.common.base

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.me.oyml.common.utils.KLog
import com.me.oyml.common.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend (Exception) -> Unit

open class BaseViewModel : ViewModel() {

    // 用于通知 UI 是否需要登录（例如 Token 过期）。
    // 默认值为 false，当某些 API 返回 -1001（如 Token 失效）时，会设置为 true，触发登录逻辑。
    val needLogin = MutableLiveData<Boolean>().apply { value = false }

    /**
     * 启动一个协程任务
     * block：要执行的协程任务（suspend 函数）。
     * error（可选）：错误回调，用于自定义错误处理。
     * cancel（可选）：协程取消时的回调。
     * showErrorToast（默认 true）：是否显示错误 Toast。
     */
    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        cancel: Cancel? = null,
        showErrorToast: Boolean = true,
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this) // 协程取消
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e) // 协程取消
                    }

                    else -> {
                        onError(e, showErrorToast) // 统一错误处理
                        error?.invoke(e) // 自定义错误回调
                    }
                }
            }
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    @SuppressLint("WrongConstant")
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            is ApiException -> {
                when (e.code) {
                    -1001 -> {
                        if (showErrorToast) {
                            Toast.makeText(Utils.getApp(), e.message, 1000).show()
                        }
                        needLogin.value = true
                    }
                    // 其他错误
                    else -> {
                        if (showErrorToast) Toast.makeText(Utils.getApp(), e.message, 1000)
                            .show()
                    }
                }
                KLog.e(e.toString())
            }
            // 网络请求失败
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException -> {
                if (showErrorToast) Toast.makeText(Utils.getApp(), "网络请求失败", 1000).show()
                KLog.e("网络请求失败$e")
            }
            // 数据解析错误
            is JsonParseException -> {
                KLog.e("数据解析错误$e")
            }
            // 其他错误
            else -> {
                KLog.e("其他错误$e")
            }
        }
    }
}
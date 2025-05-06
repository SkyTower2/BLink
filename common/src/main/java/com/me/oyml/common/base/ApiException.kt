package com.me.oyml.common.base

/**
 * 自定义 API 异常类，用于封装服务器返回的错误信息
 * @param code 错误码（通常由后端定义）
 * @param message 错误信息
 * @param cause 原始异常（可选）
 */
class ApiException(
    val code: Int,
    override val message: String?,
    cause: Throwable? = null
) : Exception(message, cause) {

    // 可选：添加更多业务错误码常量
    companion object {
        const val CODE_TOKEN_EXPIRED = -1001 // Token过期
        const val CODE_SERVER_ERROR = 500    // 服务器错误
        const val CODE_NOT_FOUND = 404       // 资源不存在
    }

    override fun toString(): String {
        return "ApiException(code=$code, message=$message, cause=$cause)"
    }
}
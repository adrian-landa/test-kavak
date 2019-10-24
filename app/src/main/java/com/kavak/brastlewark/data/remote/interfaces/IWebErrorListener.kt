package com.kavak.brastlewark.data.remote.interfaces

import com.kavak.brastlewark.enums.ExceptionType

interface IWebErrorListener {
    fun handleException(type: ExceptionType, code: Int?, message: String?)
}
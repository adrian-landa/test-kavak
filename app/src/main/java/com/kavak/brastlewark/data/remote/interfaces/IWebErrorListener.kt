package mx.hunka.brastlewark.data.remote.interfaces

import mx.hunka.brastlewark.enums.ExceptionType

interface IWebErrorListener {
    fun handleException(type: ExceptionType, code: Int?, message: String?)
}
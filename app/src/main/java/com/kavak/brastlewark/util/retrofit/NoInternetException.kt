package com.kavak.brastlewark.util.retrofit

import java.io.IOException

class NoInternetException : IOException() {
    override fun getLocalizedMessage(): String {
        return "No hay Internet"
    }

    override val message: String?
        get() = "No hay Internet"
}
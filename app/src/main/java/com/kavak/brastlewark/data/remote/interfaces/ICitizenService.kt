package com.kavak.brastlewark.data.remote.interfaces

import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.enums.ExceptionType

interface ICitizenService {
    fun getCitizens(
        onResponse: (
            payload: List<Citizen>?
        ) -> Unit,
        onException: (
            type: ExceptionType,
            code: Int?,
            message: String?
        ) -> Unit
    )
}
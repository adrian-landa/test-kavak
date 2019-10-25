package com.kavak.brastlewark.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.kavak.brastlewark.data.dao.CitizenDAO
import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.data.remote.interfaces.ICitizenService
import com.kavak.brastlewark.enums.ExceptionType
import kotlinx.coroutines.*

class CitizenRepository(val remote: ICitizenService, private val dao: CitizenDAO) {

    companion object {
        @Volatile
        private var instance: CitizenRepository? = null

        fun getInstance(remote: ICitizenService, ticketDAO: CitizenDAO) =
            instance ?: synchronized(this) {
                instance ?: CitizenRepository(remote, ticketDAO).also {
                    instance = it
                }
            }
    }



}

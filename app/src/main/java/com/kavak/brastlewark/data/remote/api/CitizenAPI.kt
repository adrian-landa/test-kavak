package com.kavak.brastlewark.data.remote.api

import com.kavak.brastlewark.constans.Web
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface CitizenAPI {
    @GET(Web.URL_SERVICE_GET_CITIZENS)
    fun getCitizens():Flowable<ResponseBody>
}
package com.kavak.brastlewark.data.remote.implementation

import android.content.Context
import com.kavak.brastlewark.constans.Json
import com.kavak.brastlewark.constans.Web
import com.kavak.brastlewark.data.entities.Citizen
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.kavak.brastlewark.data.remote.api.CitizenAPI
import com.kavak.brastlewark.enums.ExceptionType
import com.kavak.brastlewark.data.remote.interfaces.ICitizenService
import com.kavak.brastlewark.util.retrofit.NoInternetException
import com.kavak.brastlewark.util.retrofit.RetrofitFactory
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


class CitizenService(val ctx: Context, val composite: CompositeDisposable) : ICitizenService {

    private val api: CitizenAPI =
        RetrofitFactory.makeService(Web.URL_BASE, CitizenAPI::class.java, ctx)

    override fun getCitizens(
        onResponse: (payload: List<Citizen>?) -> Unit,
        onException: (type: ExceptionType, code: Int?, message: String?) -> Unit
    ) {
        val request  = api.getCitizens()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                val json = JSONObject(response.string())
                val data = json.getJSONArray(Json.LABEL_BRASTLEWARK)
                val moshi: Moshi = Moshi.Builder().build()
                val listType = Types.newParameterizedType(List::class.java, Citizen::class.java)
                val adapter = moshi.adapter<List<Citizen>>(listType)
                val citizens = adapter.fromJson(data.toString())
                onResponse.invoke(citizens)
            },
                { error ->
                    when (error) {
                        is HttpException -> onException.invoke(
                            ExceptionType.HTTP,
                            error.code(),
                            error.message
                        )
                        is SocketTimeoutException -> onException.invoke(
                            ExceptionType.TIME_OUT,
                            null,
                            error.message
                        )
                        is NoInternetException -> onException.invoke(
                            ExceptionType.NO_INTERNET,
                            null,
                            error.message
                        )
                        is IOException -> onException.invoke(ExceptionType.IO, null, error.message)
                        else -> {
                        }
                    }
                },
                {})
        composite.add(request)

    }

}
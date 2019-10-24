package com.kavak.brastlewark.viewmodels

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.data.remote.implementation.CitizenService
import com.kavak.brastlewark.data.remote.interfaces.ICitizenService
import com.kavak.brastlewark.enums.ExceptionType
import com.kavak.brastlewark.interfaces.IHome
import com.kavak.brastlewark.util.WrapperEvent
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(context: Context) : ViewModel(), IHome.UseCases, IHome.RequestListener {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val citizens: MutableLiveData<List<Citizen>> = MutableLiveData()
    val webError: MutableLiveData<WrapperEvent<String>> = MutableLiveData()

    private val disposable = CompositeDisposable()
    private val remote: ICitizenService = CitizenService(context, disposable)

    override fun getCitizens() {
        loading.value = true
        remote.getCitizens(this::onFetchResponse,this::handleException)
    }

    override fun onCitizenSelected(citizen: Citizen) {
    }

    override fun onFetchResponse(payload: List<Citizen>?) {
        loading.value = false
        citizens.value = payload ?: ArrayList()
    }

    override fun handleException(type: ExceptionType, code: Int?, message: String?) {
        loading.value = false
        webError.value = WrapperEvent(message ?: "Error Web")
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


}

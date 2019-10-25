package com.kavak.brastlewark.viewmodels

import android.content.Context
import android.text.Editable
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kavak.brastlewark.config.AppDB
import com.kavak.brastlewark.constans.Constants
import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.data.remote.implementation.CitizenService
import com.kavak.brastlewark.data.remote.interfaces.ICitizenService
import com.kavak.brastlewark.enums.ExceptionType
import com.kavak.brastlewark.interfaces.IHome
import com.kavak.brastlewark.ui.detail.DetailBottomSheetFragment
import com.kavak.brastlewark.ui.filters.FilterDialogFragment
import com.kavak.brastlewark.util.SharedPreferencesManager
import com.kavak.brastlewark.util.WrapperEvent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_filter.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

class HomeViewModel(private val context: Context) : ViewModel(), IHome.UseCases,
    IHome.RequestListener {


    private val job = Job()
    private val mainThread = CoroutineScope(job + Dispatchers.Main)
    private val ioThread = CoroutineScope(job + Dispatchers.IO)

    private val dao = AppDB.getInstance(context).citizenDAO()
    private val disposable = CompositeDisposable()
    private val remote: ICitizenService = CitizenService(context, disposable)

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    var citizens: MutableLiveData<List<Citizen>> = MutableLiveData()
    val citizenDialog: MutableLiveData<WrapperEvent<DialogFragment>> = MutableLiveData()
    val filterDialog: MutableLiveData<WrapperEvent<DialogFragment>> = MutableLiveData()
    val webError: MutableLiveData<WrapperEvent<String>> = MutableLiveData()
    val isSearchVisible: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun getCitizens() {
        loading.value = true
        remote.getCitizens(this::onFetchResponse, this::handleException)
    }

    override fun onCitizenSelected(citizen: Citizen) {
        citizenDialog.value = WrapperEvent(DetailBottomSheetFragment.newInstance(citizen))
    }

    /**
     * Method used to open the filter dialog
     */
    override fun onFilterIconClick() {
        ioThread.launch {
            val minAge = dao.getMinAge()
            val maxAge = dao.getMaxAge()
            val minHeight = dao.getMinHeight()
            val maxHeight = dao.getMaxHeight()
            val minWeight = dao.getMinWeight()
            val maxWeight = dao.getMaxWeight()

            val dialog = FilterDialogFragment.newInstance(
                minAge = minAge,
                maxAge = maxAge,
                minHeight = minHeight,
                maxHeight = maxHeight,
                minWeight = minWeight,
                maxWeight = maxWeight
            )
            mainThread.launch {
                filterDialog.value = WrapperEvent(dialog)
            }
        }
    }

    /**
     * Method used to search on the data available
     * @param query: value to search
     */
    override fun onQueryTyped(query: Editable?) {
        loading.value = true
        ioThread.launch {
            val list = if (query != null && query.isNotBlank()) {
                dao.getCitizensByName(query.trim().toString())
            } else {
                dao.getAll()
            }
            mainThread.launch {
                loading.value = false
                citizens.value = list
            }
        }
    }

    /**
     * Method used to toggle the search edit field
     */
    override fun onSearchIconClick() {
        val tmpValue = isSearchVisible.value ?: false
        isSearchVisible.value = !tmpValue
    }

    /**
     * Method used to filter the data by the values weight, height, age
     */
    override fun onFilterApplied() {
        loading.value = true
        val filterAge = SharedPreferencesManager[context, Constants.SPM_FILTER_AGE, 0]
        val filterHeight = SharedPreferencesManager[context, Constants.SPM_FILTER_HEIGHT, 0]
        val filterWeight = SharedPreferencesManager[context, Constants.SPM_FILTER_WEIGHT, 0]
        ioThread.launch {
            val list = dao.filter(filterAge = filterAge,filterHeight = filterHeight,filterWeight = filterWeight)
            mainThread.launch {
                loading.value= false
                citizens.value = list
            }
        }
    }

    override fun onFetchResponse(payload: List<Citizen>?) {
        ioThread.launch {
            dao.insert(payload ?: ArrayList())
            val list = dao.getAll()
            mainThread.launch {
                loading.value = false
                citizens.value = list
            }
        }
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

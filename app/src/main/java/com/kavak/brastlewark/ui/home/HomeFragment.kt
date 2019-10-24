package com.kavak.brastlewark.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kavak.brastlewark.R
import com.kavak.brastlewark.interfaces.IView
import com.kavak.brastlewark.util.getViewModel
import com.kavak.brastlewark.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),IView{
    private val viewmodel: HomeViewModel by lazy { getViewModel { HomeViewModel(context!!) } }

    companion object {
        fun newInstance() = HomeFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
    }


    override fun setData() {

        viewmodel.getCitizens()

        viewmodel.loading.observe(this, Observer { isLoading ->
            val visibility = if (isLoading) View.VISIBLE else View.GONE
            progressBar.visibility = visibility
        })

    }

    override fun setListeners() {
    }

}

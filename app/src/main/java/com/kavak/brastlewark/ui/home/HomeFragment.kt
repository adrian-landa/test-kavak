package com.kavak.brastlewark.ui.home

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kavak.brastlewark.R
import com.kavak.brastlewark.constans.Constants
import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.interfaces.IRecyclerListener
import com.kavak.brastlewark.interfaces.IView
import com.kavak.brastlewark.ui.adapters.CitizensAdapter
import com.kavak.brastlewark.ui.adapters.decorators.SpaceDecorator
import com.kavak.brastlewark.util.getViewModel
import com.kavak.brastlewark.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.kavak.brastlewark.interfaces.IDialogListener
import com.kavak.brastlewark.ui.filters.FilterDialogFragment


class HomeFragment : Fragment(), IView, IRecyclerListener<Citizen>, IDialogListener {

    private val viewmodel: HomeViewModel by lazy { getViewModel { HomeViewModel(context!!) } }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun setData() {
        setToolbar()
        val ctx = context
        val adapter = CitizensAdapter(listener = this)
        recyclerCitizens.adapter = adapter
        recyclerCitizens.layoutManager = LinearLayoutManager(ctx)
        recyclerCitizens.addItemDecoration(
            SpaceDecorator(
                resources.getDimension(R.dimen.dimen_short).toInt()
            )
        )

        viewmodel.getCitizens()

        viewmodel.loading.observe(this, Observer { isLoading ->
            val visibility = if (isLoading) View.VISIBLE else View.GONE
            progressBar.visibility = visibility
        })

        viewmodel.citizens.observe(this, Observer { list ->
            adapter.submitList(list)
        })

        viewmodel.citizenDialog.observe(this, Observer { dialog ->
            dialog.getContentIfNotHandled()
                ?.show(fragmentManager!!, Constants.TAG_FRAGMENT_DIALOG_DETAIL)
        })

        viewmodel.isSearchVisible.observe(this, Observer { isVisible ->
            edtSearch.visibility = if (isVisible) View.VISIBLE else View.GONE
        })

    }

    override fun setListeners() {
        edtSearch.addTextChangedListener { text: Editable? -> viewmodel.onQueryTyped(text) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itmSearch -> {
                viewmodel.onSearchIconClick()
                true
            }
            R.id.itmFilter -> {
                val dialog = FilterDialogFragment.newInstance()
                dialog.setTargetFragment(this, Constants.REQUEST_CODE_DIALOG_FILTER)
                dialog.show(fragmentManager!!, Constants.TAG_FRAGMENT_DIALOG_FILTER)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(item: Citizen) {
        viewmodel.onCitizenSelected(item)
    }

    override fun onAcceptClickListener(tag: String) {
        val dialog = fragmentManager?.findFragmentByTag(tag)
        if (dialog is DialogFragment?) {
            dialog?.dismiss()
        }
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }


}

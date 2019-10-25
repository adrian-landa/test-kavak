package com.kavak.brastlewark.ui.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import com.google.android.material.snackbar.Snackbar
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
            imgNotFound.visibility = if (list.isNotEmpty()) View.GONE else View.VISIBLE
            llErrorContainer.visibility = View.GONE
        })

        viewmodel.citizenDialog.observe(this, Observer { dialog ->
            dialog.getContentIfNotHandled()
                ?.show(fragmentManager!!, Constants.TAG_FRAGMENT_DIALOG_DETAIL)
        })

        viewmodel.filterDialog.observe(this, Observer { dialog ->
            val content = dialog.getContentIfNotHandled()
            content?.setTargetFragment(this, Constants.REQUEST_CODE_DIALOG_FILTER)
            content?.show(fragmentManager!!, Constants.TAG_FRAGMENT_DIALOG_FILTER)
        })

        viewmodel.isSearchVisible.observe(this, Observer { isVisible ->
            tilSearch.visibility = if (isVisible) View.VISIBLE else View.GONE
        })

        viewmodel.webError.observe(this, Observer { error ->
            llErrorContainer.visibility = View.VISIBLE

            error.getContentIfNotHandled()?.let { message ->
                Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun setListeners() {
        edtSearch.addTextChangedListener { text: Editable? -> viewmodel.onQueryTyped(text) }
        recyclerCitizens.setOnTouchListener { _, _ ->
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val windowToken = edtSearch.windowToken
            if (windowToken != null) {
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            }
            false
        }
        llErrorContainer.setOnClickListener { v ->
            v.visibility = View.GONE
            viewmodel.getCitizens()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itmSearch -> {
                val isVisible = viewmodel.isSearchVisible.value ?: true
                val icon = if (isVisible) R.drawable.ic_search else R.drawable.ic_clear
                item.setIcon(icon)
                edtSearch.setText("")
                viewmodel.onSearchIconClick()
                true
            }
            R.id.itmFilter -> {
                viewmodel.onFilterIconClick()
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
        viewmodel.onFilterApplied()
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }



}

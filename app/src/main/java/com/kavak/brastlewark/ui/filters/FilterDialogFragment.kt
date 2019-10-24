package com.kavak.brastlewark.ui.filters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kavak.brastlewark.R
import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.interfaces.IDialogListener
import com.kavak.brastlewark.interfaces.IView
import kotlinx.android.synthetic.main.dialog_filter.*
import kotlinx.android.synthetic.main.fragment_detail.*

class FilterDialogFragment : DialogFragment(), IView {
    private val listener: IDialogListener? by lazy {
        when {
            activity is IDialogListener -> activity as IDialogListener
            targetFragment is IDialogListener -> targetFragment as IDialogListener
            parentFragment is IDialogListener -> parentFragment as IDialogListener
            else -> null
        }
    }

    companion object {
        fun newInstance(): DialogFragment {
            val dialog = FilterDialogFragment()
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
    }

    override fun setData() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = ViewGroup.LayoutParams.WRAP_CONTENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)

    }

    override fun setListeners() {
        btnApply.setOnClickListener { listener?.onAcceptClickListener(tag ?: "") }
    }

}
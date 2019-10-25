package com.kavak.brastlewark.ui.filters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import com.kavak.brastlewark.R
import com.kavak.brastlewark.constans.Constants
import com.kavak.brastlewark.interfaces.IDialogListener
import com.kavak.brastlewark.interfaces.IView
import com.kavak.brastlewark.util.SharedPreferencesManager
import kotlinx.android.synthetic.main.dialog_filter.*

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
        fun newInstance(
            minAge: Int, maxAge: Int,
            minHeight: Float, maxHeight: Float,
            minWeight: Float, maxWeight: Float
        ): DialogFragment {
            val dialog = FilterDialogFragment()
            val arguments = Bundle()
            arguments.putInt(Constants.KEY_MIN_AGE, minAge)
            arguments.putInt(Constants.KEY_MAX_AGE, maxAge)
            arguments.putFloat(Constants.KEY_MIN_HEIGHT, minHeight)
            arguments.putFloat(Constants.KEY_MAX_HEIGHT, maxHeight)
            arguments.putFloat(Constants.KEY_MIN_WEIGHT, minWeight)
            arguments.putFloat(Constants.KEY_MAX_WEIGHT, maxWeight)
            dialog.arguments = arguments
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
        val tmpArguments = arguments
        if (tmpArguments != null) {
            val minAge = tmpArguments.getInt(Constants.KEY_MIN_AGE, 0)
            val maxAge = tmpArguments.getInt(Constants.KEY_MAX_AGE, 0)
            val minHeight = tmpArguments.getFloat(Constants.KEY_MIN_HEIGHT, 0f)
            val maxHeight = tmpArguments.getFloat(Constants.KEY_MAX_HEIGHT, 0f)
            val minWeight = tmpArguments.getFloat(Constants.KEY_MIN_WEIGHT, 0f)
            val maxWeight = tmpArguments.getFloat(Constants.KEY_MAX_WEIGHT, 0f)

            seekbarAge.max = maxAge
            seekbarWeight.max = maxWeight.toInt()
            seekbarHeight.max = maxHeight.toInt()

            tvFilterAgeMin.text = getString(R.string.template_min, minAge.toFloat())
            tvFilterAgeMax.text = getString(R.string.template_max, maxAge.toFloat())
            tvFilterHeightMin.text = getString(R.string.template_min, minHeight)
            tvFilterHeightMax.text = getString(R.string.template_max, maxHeight)
            tvFilterWeightMin.text = getString(R.string.template_min, minWeight)
            tvFilterWeightMax.text = getString(R.string.template_max, maxWeight)

        }
    }

    override fun setListeners() {
        btnApply.setOnClickListener {
            context?.let { ctx ->
                Log.i("logApp",seekbarAge.progress.toString())
                SharedPreferencesManager[ctx, Constants.SPM_FILTER_AGE] = seekbarAge.progress
                SharedPreferencesManager[ctx, Constants.SPM_FILTER_HEIGHT] = seekbarHeight.progress
                SharedPreferencesManager[ctx, Constants.SPM_FILTER_WEIGHT] = seekbarWeight.progress
            }
            listener?.onAcceptClickListener(tag ?: "")
        }

        seekbarAge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val minAge = arguments?.getInt(Constants.KEY_MIN_AGE, 0) ?: 0
                val value = if (progress > 0) progress else minAge
                tvFilterAgeCurrent.text = getString(R.string.template_val, value.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        seekbarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val minHeight = arguments?.getFloat(Constants.KEY_MIN_HEIGHT, 0f) ?: 0f
                val value = if (progress > 0) progress.toFloat() else minHeight
                tvFilterHeightCurrent.text = getString(R.string.template_val, value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekbarWeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val minWeight = arguments?.getFloat(Constants.KEY_MIN_WEIGHT, 0f) ?: 0f
                val value = if (progress > 0) progress.toFloat() else minWeight
                tvFilterWeightCurrent.text = getString(R.string.template_val, value)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

}
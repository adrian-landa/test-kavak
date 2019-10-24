package com.kavak.brastlewark.ui.detail

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
import com.kavak.brastlewark.interfaces.IView
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailBottomSheetFragment : BottomSheetDialogFragment(), IView {
    var citizen: Citizen? = null

    companion object {
        fun newInstance(citizen: Citizen): DialogFragment {
            val dialog = DetailBottomSheetFragment()
            dialog.citizen = citizen
            return dialog
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
    }

    override fun setData() {
        val tmpCitizen = this.citizen
        if (tmpCitizen != null) {
            context?.let { ctx ->
                Glide.with(this)
                    .load(tmpCitizen.thumbnail)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_gnome).error(R.drawable.ic_gnome))
                    .into(imgCitizenThumbnail)
                tvCitizenName.text = tmpCitizen.name
                tvCitizenHeight.text = ctx.getString(R.string.template_height, tmpCitizen.height)
                tvCitizenWeight.text = ctx.getString(R.string.template_weight, tmpCitizen.weight)
                tvCitizenAge.text = ctx.getString(R.string.template_age, tmpCitizen.age)
                tvCitizenHair.text = ctx.getString(R.string.template_hair_color,tmpCitizen.hairColor)
                tvCitizenJobs.text = tmpCitizen.professions.fold("") { acc, item -> "$acc,$item" }
                tvCitizenFriends.text = tmpCitizen.friends.fold("") { acc, item -> "$acc,$item" }
            }
        }
    }

    override fun setListeners() {
    }

}
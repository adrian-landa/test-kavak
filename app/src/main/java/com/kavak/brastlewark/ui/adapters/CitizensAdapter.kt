package com.kavak.brastlewark.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kavak.brastlewark.R
import com.kavak.brastlewark.data.entities.Citizen
import com.kavak.brastlewark.interfaces.IRecyclerListener
import kotlinx.android.synthetic.main.item_citizen.view.*

class CitizensAdapter(private val listener: IRecyclerListener<Citizen>? = null) :
    ListAdapter<Citizen, CitizensAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_citizen, parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itm = getItem(position)
        itm?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Citizen) {
            val ctx = itemView.context
            Glide.with(itemView)
                .load(item.thumbnail)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_gnome).error(R.drawable.ic_gnome))
                .into(itemView.imgCitizenThumbnail)
            itemView.tvCitizenName.text = item.name
            itemView.tvCitizenHeight.text = ctx.getString(R.string.template_height, item.height)
            itemView.tvCitizenWeight.text = ctx.getString(R.string.template_weight, item.weight)
            itemView.tvCitizenAge.text = ctx.getString(R.string.template_age, item.age)
            itemView.tvCitizenJobs.text =
                ctx.getString(R.string.template_jobs, item.professions.size)
            itemView.setOnClickListener { listener?.onItemClick(item) }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Citizen>() {
            override fun areItemsTheSame(oldItem: Citizen, newItem: Citizen): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Citizen, newItem: Citizen): Boolean =
                oldItem == newItem
        }
    }
}
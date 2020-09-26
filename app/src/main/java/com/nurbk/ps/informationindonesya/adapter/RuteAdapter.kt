package com.nurbk.ps.informationindonesya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.ItemListWisataBinding
import com.nurbk.ps.informationindonesya.model.RuteAngkotContentc
import com.nurbk.ps.informationindonesya.util.Constant
import kotlinx.android.synthetic.main.item_list_wisata.view.*

class RuteAdapter(val data: ArrayList<RuteAngkotContentc>, val onSelectData: OnSelectData) :
    RecyclerView.Adapter<RuteAdapter.ViewHolder>() {


    class ViewHolder(item: ItemListWisataBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_wisata, parent, false
            )
        )
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val conte = data[position]

        holder.itemView.apply {

            Constant.setImage(
                holder.itemView.context,
                conte.gambarUrl,
                imgWisata,
                R.color.colorCard
            )

            tvWisata.text = conte.trayek
            tvKategori.text = conte.lintasan

            setOnClickListener {
                onSelectData.onSelect(conte)
            }

        }
    }

    interface OnSelectData {
        fun onSelect(content: RuteAngkotContentc)
    }

}
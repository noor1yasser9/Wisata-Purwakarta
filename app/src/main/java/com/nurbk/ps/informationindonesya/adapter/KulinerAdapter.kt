package com.nurbk.ps.informationindonesya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.ItemListKulinerBinding
import com.nurbk.ps.informationindonesya.model.KulinerContent
import com.nurbk.ps.informationindonesya.util.Constant
import kotlinx.android.synthetic.main.item_list_kuliner.view.*

class KulinerAdapter(val data: ArrayList<KulinerContent>, val onSelectData: OnSelectData) :
    RecyclerView.Adapter<KulinerAdapter.ViewHolder>() {


    class ViewHolder(item: ItemListKulinerBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_kuliner, parent, false
            )
        )
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cooking = data[position]

        holder.itemView.apply {

            Constant.setImage(
                holder.itemView.context,
                cooking.gambarUrl,
                imgKuliner,
                R.color.colorCard
            )

            tvKuliner.text = cooking.nama
            tvKategori.text = cooking.kategori

            setOnClickListener {
                onSelectData.onSelect(cooking)
            }

        }
    }

    interface OnSelectData {
        fun onSelect(hotel: KulinerContent)
    }

}
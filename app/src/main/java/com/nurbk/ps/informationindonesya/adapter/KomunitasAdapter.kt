package com.nurbk.ps.informationindonesya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.ItemListKulinerBinding
import com.nurbk.ps.informationindonesya.databinding.ItemListWisataBinding
import com.nurbk.ps.informationindonesya.model.KomunitaContent
import com.nurbk.ps.informationindonesya.model.KulinerContent
import com.nurbk.ps.informationindonesya.model.WisataContent
import com.nurbk.ps.informationindonesya.util.Constant
import kotlinx.android.synthetic.main.item_list_wisata.view.*

class KomunitasAdapter(val data: ArrayList<KomunitaContent>, val onSelectData: OnSelectData) :
    RecyclerView.Adapter<KomunitasAdapter.ViewHolder>() {


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

        val cooking = data[position]

        holder.itemView.apply {

            Constant.setImage(
                holder.itemView.context,
                cooking.logoUrl,
                imgWisata,
                R.color.colorCard
            )

            tvWisata.text = cooking.nama
            tvKategori.text = cooking.kategori

            setOnClickListener {
                onSelectData.onSelect(cooking)
            }

        }
    }

    interface OnSelectData {
        fun onSelect(content: KomunitaContent)
    }

}
package com.nurbk.ps.informationindonesya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.ItemListHotelBinding
import com.nurbk.ps.informationindonesya.model.HotelContent
import com.nurbk.ps.informationindonesya.util.Constant
import kotlinx.android.synthetic.main.item_list_hotel.view.*

class HotelAdapter(val data: ArrayList<HotelContent>, val onSelectData: OnSelectData) :
    RecyclerView.Adapter<HotelAdapter.ViewHolder>() {


    class ViewHolder(item: ItemListHotelBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_hotel, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val hotel = data[position]

        holder.itemView.apply {

            Constant.setImage(
                holder.itemView.context,
                hotel.gambarUrl,
                imgHotel,
                R.color.colorCard
            )

            tvNamaHotel.text = hotel.nama

            setOnClickListener {
                onSelectData.onSelect(hotel)
            }

        }

    }

    override fun getItemCount() = data.size

    interface OnSelectData {
        fun onSelect(hotel: HotelContent)
    }
}
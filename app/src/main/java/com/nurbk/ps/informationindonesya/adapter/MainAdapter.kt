package com.nurbk.ps.informationindonesya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.ItemGridMainBinding
import com.nurbk.ps.informationindonesya.model.ModelMain
import kotlinx.android.synthetic.main.item_grid_main.view.*

class MainAdapter(val data: ArrayList<ModelMain>, val onSelectData: OnSelectData) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(item: ItemGridMainBinding) : RecyclerView.ViewHolder(item.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_grid_main, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val modelMain = data[position]
        holder.itemView.apply {
            imgMainData.setImageResource(modelMain.image)
            tvMainData.text = modelMain.name
            setOnClickListener {
                onSelectData.onSelected(modelMain)
            }
        }

    }

    interface OnSelectData {
        fun onSelected(mdlMain: ModelMain)
    }


    override fun getItemCount() = data.size

}
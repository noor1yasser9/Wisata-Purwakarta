package com.nurbk.ps.informationindonesya.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.ItemListPrayPlaceBinding
import com.nurbk.ps.informationindonesya.model.TempatIbadahContent
import kotlinx.android.synthetic.main.item_list_pray_place.view.*


class PrayPlaceAdapter(var items: ArrayList<TempatIbadahContent>) :
    RecyclerView.Adapter<PrayPlaceAdapter.ViewHolder>() {

    private var mMap: GoogleMap? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list_pray_place, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        holder.itemView.apply {
            mapView.apply {
                mapView(
                    this, holder.itemView.context,
                    data.latitude.toDouble(),
                    data.longitude.toDouble(),
                    data.jenis + " : " + data.nama
                )

            }
            mapView.onResume()
            txtPlaceName.text = data.nama
        }
    }


    private fun mapView(
        mapView: MapView, context: Context,
        lat: Double,
        lan: Double,
        name: String
    ) {
        mapView.apply {
            onCreate(null)
            getMapAsync { googleMap ->
                mMap = googleMap

                MapsInitializer.initialize(context)
                googleMap.uiSettings.isMapToolbarEnabled = false
                try {

                    mMap!!.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.style_json
                        )
                    )

                } catch (e: Resources.NotFoundException) {
                    e.printStackTrace()
                }
                val latLng = LatLng(lat, lan)
                mMap!!.addMarker(MarkerOptions().position(latLng).title(name))
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                mMap!!.uiSettings.setAllGesturesEnabled(true)
                mMap!!.uiSettings.isZoomGesturesEnabled = true
                mMap!!.isTrafficEnabled = true
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //Class Holder
    inner class ViewHolder(item: ItemListPrayPlaceBinding) :
        RecyclerView.ViewHolder(item.root)

}
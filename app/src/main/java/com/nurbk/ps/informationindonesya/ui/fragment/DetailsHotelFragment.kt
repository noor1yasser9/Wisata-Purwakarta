package com.nurbk.ps.informationindonesya.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.directions.route.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.FragmentHotelDetailsBinding
import com.nurbk.ps.informationindonesya.model.HotelContent
import com.nurbk.ps.informationindonesya.model.KulinerContent
import com.nurbk.ps.informationindonesya.model.WisataContent
import com.nurbk.ps.informationindonesya.util.Constant.DETAILS
import com.nurbk.ps.informationindonesya.util.Constant.TYPE
import kotlinx.android.synthetic.main.fragment_hotel_details.*
import timber.log.Timber

class DetailsHotelFragment : Fragment(), RoutingListener,
    GoogleApiClient.OnConnectionFailedListener {

    private val TAG = "DetailsHotelFragment"
    private lateinit var mBinding: FragmentHotelDetailsBinding
    private var map: GoogleMap? = null
    private lateinit var client: FusedLocationProviderClient

    private var start: LatLng? = null
    private var end: LatLng? = null

    //polyline object
    private var polylines: ArrayList<Polyline>? = null

    private lateinit var hotelContent: HotelContent
    private lateinit var cooking: KulinerContent
    private lateinit var wisata: WisataContent

    private val data by lazy { requireArguments() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHotelDetailsBinding.inflate(inflater, container, false).also {
            it.executePendingBindings()
        }

        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        end = LatLng(
            31.3846,
            34.3107
        )

        if (data.getString(TYPE) == "hotel") {
            hotelContent = data.getParcelable(DETAILS)!!

            tvNamaHotel.text = hotelContent.nama
            tvAddressHotel.text = hotelContent.alamat
            tvPhoneHotel.text = hotelContent.nomorTelp
            if (hotelContent.kordinat != "-")
                end = LatLng(
                    hotelContent.kordinat.split(",")[0].toDouble(),
                    hotelContent.kordinat.split(",")[1].toDouble()
                )
        } else if (data.getString(TYPE) == "cooking") {

            cooking = data.getParcelable(DETAILS)!!
            mBinding.cooking.visibility = View.VISIBLE
            tvNamaHotel.text = cooking.nama
            tvAddressHotel.text = cooking.alamat
            tvOpenTime.text = cooking.jamBukaTutup
            tvPhoneHotel.text = getString(R.string.number)
            if (cooking.kordinat != "-")
                end = LatLng(
                    cooking.kordinat.split(",")[0].toDouble(),
                    cooking.kordinat.split(",")[1].toDouble()
                )
        } else if (data.getString(TYPE) == "Wisata") {
            ivDetailHotel.setImageResource(R.drawable.ic_dtl_desc)
            wisata = data.getParcelable(DETAILS)!!
            tvNamaHotel.text = wisata.nama
            end = LatLng(
                wisata.latitude.toDouble(),
                wisata.longitude.toDouble()
            )
            wistata.visibility = View.GONE
            tvAddressHotel.text = getString(R.string.number)

        }


        client = LocationServices.getFusedLocationProviderClient(requireActivity())


        //show maps
        mapView.getMapAsync {
            map = it

            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(end, 10f))

            it.uiSettings.setAllGesturesEnabled(true)
            it.uiSettings.isZoomGesturesEnabled = true
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isCompassEnabled = true
            it.uiSettings.isMyLocationButtonEnabled = true
            it.isTrafficEnabled = true


            try {

                it.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireActivity(), R.raw.style_json
                    )
                )

            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }
        }

        permission()
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }


    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }


    private fun permission() {
        Timber.d("permission")
        Dexter.withContext(requireContext())
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    getCurrentLocation()

                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {

                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {

            }
            .check()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val task = client.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                mapView.getMapAsync {
                    start = LatLng(location.latitude, location.longitude)
                    Findroutes(start, end)
                }
            }
        }

    }


    fun Findroutes(Start: LatLng?, End: LatLng?) {
        if (Start == null || End == null) {
            Toast.makeText(requireContext(), "Unable to get location", Toast.LENGTH_LONG).show()
        } else {
            val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(Start, End)
                .key(getString(R.string.apiKey)) //also define your api key here.
                .build()
            routing.execute()
        }
    }


    override fun onRoutingFailure(e: RouteException?) {

        Snackbar.make(requireView(), e.toString(), Snackbar.LENGTH_LONG).show()

    }

    override fun onRoutingStart() {
        Toast.makeText(requireContext(), "Finding Route...", Toast.LENGTH_LONG).show();
    }

    override fun onRoutingSuccess(route: ArrayList<Route>?, shortestRouteIndex: Int) {

        if (polylines != null) {
            polylines!!.clear()
        }
        val polyOptions = PolylineOptions()
        var polylineStartLatLng: LatLng? = null
        var polylineEndLatLng: LatLng? = null
        polylines = ArrayList()

        for (i in 0 until route!!.size) {
            if (i == shortestRouteIndex) {
                polyOptions.color(ResourcesCompat.getColor(resources, R.color.colorAccent, null))
                polyOptions.width(8f)
                polyOptions.addAll(route[shortestRouteIndex].points)
                val polyline: Polyline = map!!.addPolyline(polyOptions)
                polylineStartLatLng = polyline.points[0]
                polylineEndLatLng = polyline.points[polyline.points.size - 1]
                polylines!!.add(polyline)
            }
        }


        val startMarker = MarkerOptions()
        startMarker.position(polylineStartLatLng!!)
        startMarker.title("My Location")
        map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(polylineStartLatLng, 10f))

        map!!.addMarker(startMarker)

        val endMarker = MarkerOptions()
        endMarker.position(polylineEndLatLng!!)
        endMarker.title(
            when {
                data.getString(TYPE) == "hotel" -> {
                    hotelContent.nama
                }
                data.getString(TYPE) == "cooking" -> {
                    cooking.nama
                }
                data.getString(TYPE) == "Wisata" -> {
                    wisata.nama
                }
                else -> {
                    ""
                }
            }
        )
        map!!.addMarker(endMarker)
    }

    override fun onRoutingCancelled() {
        Findroutes(start, end)
    }


    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Findroutes(start, end)
    }
}
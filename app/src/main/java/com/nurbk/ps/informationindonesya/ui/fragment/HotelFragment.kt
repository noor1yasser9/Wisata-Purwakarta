package com.nurbk.ps.informationindonesya.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.adapter.HotelAdapter
import com.nurbk.ps.informationindonesya.databinding.FragmentHotelBinding
import com.nurbk.ps.informationindonesya.model.HotelContent
import com.nurbk.ps.informationindonesya.ui.viewmodel.HotelViewModel
import com.nurbk.ps.informationindonesya.util.Constant.DETAILS
import com.nurbk.ps.informationindonesya.util.Constant.TYPE
import com.nurbk.ps.informationindonesya.util.Constant.dialog
import com.nurbk.ps.informationindonesya.util.Constant.showDialog
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.android.synthetic.main.fragment_hotel.*
import timber.log.Timber

class HotelFragment : Fragment(), HotelAdapter.OnSelectData {

    private lateinit var mBinding: FragmentHotelBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[HotelViewModel::class.java]
    }

    private val mAdapter = HotelAdapter(ArrayList(), this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHotelBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.dataHotelLiveData.observe(viewLifecycleOwner, Observer { response ->
            Timber.d(" onViewCreated->viewModel")
            when (response) {
                is Resource.Success -> {
                    Timber.d(" onViewCreated->Resource.Success")
                    response.data?.let { data ->
                        dialog!!.hide()
                        dialog!!.dismiss()
                        mAdapter.data.clear()
                        mAdapter.data.addAll(data.hotel)
                        mAdapter.notifyDataSetChanged()
                    }

                }
                is Resource.Error -> {
                    if (dialog != null) {
                        dialog!!.hide()
                    }
                }
                is Resource.Loading -> {
                    Timber.d("onViewCreated-> Resource.Loading")
                    showDialog(requireActivity())
                }
            }

        })


        rvHotel.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSelect(hotel: HotelContent) {
        navigation(R.id.action_hotelFragment_to_detailsHotelFragment, Bundle().apply {
            putParcelable(
                DETAILS,
                hotel
            )
            putString(TYPE,"hotel")
        })
    }

    private fun navigation(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle).also {
            dialog!!.hide()
            dialog!!.dismiss()
        }
    }

}
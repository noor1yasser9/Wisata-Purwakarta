package com.nurbk.ps.informationindonesya.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.adapter.KulinerAdapter
import com.nurbk.ps.informationindonesya.adapter.WisataAdapter
import com.nurbk.ps.informationindonesya.databinding.FragmentCookingBinding
import com.nurbk.ps.informationindonesya.model.KulinerContent
import com.nurbk.ps.informationindonesya.model.WisataContent
import com.nurbk.ps.informationindonesya.ui.viewmodel.CookingViewModel
import com.nurbk.ps.informationindonesya.ui.viewmodel.WistataViewModel
import com.nurbk.ps.informationindonesya.util.Constant
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.android.synthetic.main.fragment_cooking.*
import timber.log.Timber

class WisataFragment : Fragment(), WisataAdapter.OnSelectData {

    private lateinit var mBinding: FragmentCookingBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[WistataViewModel::class.java]
    }

    private val mAdapter = WisataAdapter(ArrayList(), this)

    private var isSelect = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCookingBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        toolbar.title = "Wisata"

        viewModel.dataWisataLiveData.observe(viewLifecycleOwner, Observer { response ->
            Timber.d(" onViewCreated->viewModel")
            when (response) {
                is Resource.Success -> {
                    Timber.d(" onViewCreated->Resource.Success")
                    response.data?.let { data ->
                        Constant.dialog!!.hide()
                        Constant.dialog!!.dismiss()
                        mAdapter.data.clear()
                        mAdapter.data.addAll(data.wisata)
                        mAdapter.notifyDataSetChanged()

                    }

                }
                is Resource.Error -> {
                    if (Constant.dialog != null) {
                        Constant.dialog!!.hide()
                    }
                }
                is Resource.Loading -> {
                    Timber.d("onViewCreated-> Resource.Loading")
                    Constant.showDialog(requireActivity())
                }
            }

        })
        viewModel.dataWisataContentLiveData.observe(viewLifecycleOwner, Observer { response ->
            Timber.d(" onViewCreated->viewModel")
            when (response) {
                is Resource.Success -> {
                    Timber.d(" onViewCreated->Resource.Success")
                    response.data?.let { data ->
                        Constant.dialog!!.hide()
                        Constant.dialog!!.dismiss()

                        navigation(
                            R.id.action_wisataFragment_to_detailsHotelFragment,
                            Bundle().apply {
                                putParcelable(
                                    Constant.DETAILS,
                                    data
                                )
                                putString(Constant.TYPE, "Wisata")

                            }).also {
                            viewModel.dataWisataContentLiveData.value = null
                        }

                    }

                }
                is Resource.Error -> {
                    if (Constant.dialog != null) {
                        Constant.dialog!!.hide()
                    }
                }
                is Resource.Loading -> {
                    Timber.d("onViewCreated-> Resource.Loading")
                    Constant.showDialog(requireActivity())
                }
            }

        })


        rvCooking.apply {
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    2, RecyclerView.VERTICAL, false
                )
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSelect(content: WisataContent) {
        viewModel.getWisatas(content.id.toString())
    }

    private fun navigation(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle).also {
            Constant.dialog!!.hide()
            Constant.dialog!!.dismiss()
        }
    }


}
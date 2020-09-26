package com.nurbk.ps.informationindonesya.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurbk.ps.informationindonesya.adapter.PrayPlaceAdapter
import com.nurbk.ps.informationindonesya.databinding.FragmentPrayPlaceBinding
import com.nurbk.ps.informationindonesya.model.TempatIbadahContent
import com.nurbk.ps.informationindonesya.ui.viewmodel.PrayPlaceViewModel
import com.nurbk.ps.informationindonesya.util.Constant
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.android.synthetic.main.fragment_pray_place.*
import timber.log.Timber

class PrayPlaceFragment : Fragment(){

    private lateinit var mBinding: FragmentPrayPlaceBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[PrayPlaceViewModel::class.java]
    }

    private val mAdapter = PrayPlaceAdapter(ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPrayPlaceBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.dataTempatbadahlLiveData.observe(viewLifecycleOwner, Observer { response ->
            Timber.d(" onViewCreated->viewModel")
            when (response) {
                is Resource.Success -> {
                    Timber.d(" onViewCreated->Resource.Success")
                    response.data?.let { data ->
                        Constant.dialog!!.hide()
                        Constant.dialog!!.dismiss()
                        mAdapter.items.clear()
                        mAdapter.items.addAll(data.tempatIbadah)
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


        rvPray.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
            setHasFixedSize(true)
        }
    }


    private fun navigation(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle).also {
            Constant.dialog!!.hide()
            Constant.dialog!!.dismiss()
        }
    }
}
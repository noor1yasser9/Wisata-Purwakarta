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
import com.nurbk.ps.informationindonesya.adapter.KomunitasAdapter
import com.nurbk.ps.informationindonesya.databinding.FragmentCookingBinding
import com.nurbk.ps.informationindonesya.model.KomunitaContent
import com.nurbk.ps.informationindonesya.ui.viewmodel.KomunitasViewModel
import com.nurbk.ps.informationindonesya.util.Constant
import com.nurbk.ps.informationindonesya.util.Resource
import kotlinx.android.synthetic.main.fragment_cooking.*
import timber.log.Timber

class KomunitasFragment : Fragment(), KomunitasAdapter.OnSelectData {


    private lateinit var mBinding: FragmentCookingBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[KomunitasViewModel::class.java]
    }

    private val mAdapter = KomunitasAdapter(ArrayList(), this)

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

        viewModel.dataKomunitasLiveData.observe(viewLifecycleOwner, Observer { response ->
            Timber.d(" onViewCreated->viewModel")
            when (response) {
                is Resource.Success -> {
                    Timber.d(" onViewCreated->Resource.Success")
                    response.data?.let { data ->
                        Constant.dialog!!.hide()
                        Constant.dialog!!.dismiss()
                        mAdapter.data.clear()
                        mAdapter.data.addAll(data.komunitas)
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
        viewModel.dataKomunitasContentLiveData.observe(viewLifecycleOwner, Observer { response ->
            Timber.d(" onViewCreated->viewModel")
            when (response) {
                is Resource.Success -> {
                    Timber.d(" onViewCreated->Resource.Success")
                    response.data?.let { data ->
                        Constant.dialog!!.hide()
                        Constant.dialog!!.dismiss()

                        navigation(
                            R.id.action_komunitasFragment_to_detailsKomunitasFragment,
                            Bundle().apply {
                                putParcelable(
                                    Constant.DETAILS,
                                    data
                                )
                                putString(Constant.TYPE, "Komun")

                            }).also {
                            viewModel.dataKomunitasContentLiveData.value = null
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


    private fun navigation(id: Int, bundle: Bundle) {
        findNavController().navigate(id, bundle).also {
            Constant.dialog!!.hide()
            Constant.dialog!!.dismiss()
        }
    }

    override fun onSelect(content: KomunitaContent) {
        viewModel.getKomunitasContent(content.id.toString())
    }
}
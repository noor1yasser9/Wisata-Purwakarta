package com.nurbk.ps.informationindonesya.ui.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.adapter.MainAdapter
import com.nurbk.ps.informationindonesya.databinding.FragmentMainBinding
import com.nurbk.ps.informationindonesya.model.ModelMain
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.header_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment(), MainAdapter.OnSelectData {

    private lateinit var mBinding: FragmentMainBinding
    private val myAdapter: MainAdapter = MainAdapter(ArrayList(), this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false).also {
            it.executePendingBindings()
        }

        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        myAdapter.data.apply {
            clear()
            add(ModelMain("Hotel", R.drawable.ic_hotel))
            add(ModelMain("Kuliner", R.drawable.ic_cafe))
            add(ModelMain("Tempat Ibadah", R.drawable.ic_pray_place))
            add(ModelMain("Wisata", R.drawable.ic_destination))
            add(ModelMain("Komunitas", R.drawable.ic_komunitas))
            add(ModelMain("Rute Angkot", R.drawable.ic_rute_angkot))

        }



        rvMainMenu.apply {
            layoutManager = GridLayoutManager(
                requireContext(), 2,
                RecyclerView.VERTICAL, false
            )

            setHasFixedSize(true)
            adapter = myAdapter
        }

        tvDate.apply {
            text = DateFormat
                .format(
                    "d MMMM yyyy", Calendar
                        .getInstance().time
                )
        }
    }


    override fun onSelected(mdlMain: ModelMain) {
        when (mdlMain.name) {
            "Hotel" -> {
                navigation(R.id.action_mainFragment_to_hotelFragment)
            }
            "Kuliner"
            -> {
                navigation(R.id.action_mainFragment_to_cookingFragment)

            }
            "Tempat Ibadah"
            -> {
                navigation(R.id.action_mainFragment_to_prayPlaceFragment)
            }
            "Wisata"
            -> {
                navigation(R.id.action_mainFragment_to_wisataFragment)
            }
            "Komunitas"
            -> {
                navigation(R.id.action_mainFragment_to_komunitasFragment)

            }
            "Rute Angkot"
            -> {
                navigation(R.id.action_mainFragment_to_ruteangkotFragment)

            }
        }
    }

    private fun navigation(id: Int) {
        findNavController().navigate(id)
    }

}
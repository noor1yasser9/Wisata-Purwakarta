package com.nurbk.ps.informationindonesya.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurbk.ps.informationindonesya.R
import com.nurbk.ps.informationindonesya.databinding.FragmentDetailsKomunitasBinding
import com.nurbk.ps.informationindonesya.model.KomunitaContent
import com.nurbk.ps.informationindonesya.model.RuteAngkotContentc
import com.nurbk.ps.informationindonesya.util.Constant
import com.nurbk.ps.informationindonesya.util.Constant.DETAILS
import com.nurbk.ps.informationindonesya.util.Constant.TYPE
import kotlinx.android.synthetic.main.fragment_details_komunitas.*
import kotlinx.android.synthetic.main.item_list_kuliner.view.*

class DetailsKomunitasFragment : Fragment() {

    private lateinit var mBinding: FragmentDetailsKomunitasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDetailsKomunitasBinding.inflate(inflater, container, false).also {
            it.executePendingBindings()
        }

        return mBinding.root
    }

    private lateinit var content: KomunitaContent
    private lateinit var contentRute: RuteAngkotContentc

    private val bundel by lazy {
        requireArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        if (bundel.getString(TYPE) == "Komun") {
            content = bundel.getParcelable(DETAILS)!!
            tvNamaHotel.text = content.nama
            tvPhoneHotel.text = content.kategori
            tvOpenTime.text = content.deskripsi
            tvAddressHotel.text = content.kontak
            tvAddressHotel.text = content.kontak
            instagram.text = content.instagram
            facebook.text = content.facebook


            Constant.setImage(
                requireContext(),
                content.logoUrl,
                imgWisata,
                R.color.colorCard
            )
        } else {
            contentRute = bundel.getParcelable(DETAILS)!!
            tvNamaHotel.text = contentRute.trayek
            tvOpenTime.text = contentRute.lintasan
            category.visibility = View.GONE


            Constant.setImage(
                requireContext(),
                contentRute.gambarUrl,
                imgWisata,
                R.color.colorCard
            )
        }
    }
}
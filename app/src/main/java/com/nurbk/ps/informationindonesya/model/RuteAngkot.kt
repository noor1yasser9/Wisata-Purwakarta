package com.nurbk.ps.informationindonesya.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RuteAngkot(
    @SerializedName("rute_angkot")
    var ruteAngkot: List<RuteAngkotContentc>
)

@Parcelize
data class RuteAngkotContentc(
    @SerializedName("gambar_url")
    var gambarUrl: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("lintasan")
    var lintasan: String,
    @SerializedName("nomor_angkot")
    var nomorAngkot: String,
    @SerializedName("trayek")
    var trayek: String
) : Parcelable
package com.nurbk.ps.informationindonesya.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class kuliner(
    @SerializedName("kuliner")
    var kuliner: List<KulinerContent>
)

@Parcelize
data class KulinerContent(
    @SerializedName("alamat")
    var alamat: String,
    @SerializedName("gambar_url")
    var gambarUrl: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("jam_buka_tutup")
    var jamBukaTutup: String,
    @SerializedName("kategori")
    var kategori: String,
    @SerializedName("kordinat")
    var kordinat: String,
    @SerializedName("nama")
    var nama: String
) : Parcelable
package com.nurbk.ps.informationindonesya.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class komunitas(
    @SerializedName("komunitas")
    var komunitas: List<KomunitaContent>
)


@Parcelize
data class KomunitaContent(
    @SerializedName("deskripsi")
    var deskripsi: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("kategori")
    var kategori: String,
    @SerializedName("logo_url")
    var logoUrl: String,
    @SerializedName("jadwal")
    var jadwal: String,
    @SerializedName("kontak")
    var kontak: String,
    @SerializedName("instagram")
    var instagram: String,
    @SerializedName("facebook")
    var facebook: String,
    @SerializedName("nama")
    var nama: String

) : Parcelable
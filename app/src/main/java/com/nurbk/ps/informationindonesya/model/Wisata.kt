package com.nurbk.ps.informationindonesya.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Wisata(
    @SerializedName("wisata")
    var wisata: List<WisataContent>
)

@Parcelize
data class WisataContent(
    @SerializedName("gambar_url")
    var gambarUrl: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("kategori")
    var kategori: String,
    @SerializedName("deskripsi")
    var deskripsi: String,
    @SerializedName("photo_by")
    var photo_by: String,
    @SerializedName("latitude")
    var latitude: String,
    @SerializedName("longitude")
    var longitude: String,
    @SerializedName("nama")
    var nama: String
) : Parcelable